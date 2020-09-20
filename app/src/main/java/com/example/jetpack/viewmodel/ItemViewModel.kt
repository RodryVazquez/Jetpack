package com.example.jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.jetpack.model.ItemApiService
import com.example.jetpack.model.ItemBreed
import com.example.jetpack.model.persistence.ItemDatabase
import com.example.jetpack.util.SharePreferenceUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : BaseViewModel(application) {

    private val itemService = ItemApiService()
    private val disposable = CompositeDisposable()

    private var sharePreferenceUtil = SharePreferenceUtil(getApplication())

    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    val itemList = MutableLiveData<List<ItemBreed>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val updatedTime = sharePreferenceUtil.getUpdateTime()
        if (updatedTime != null && updatedTime != 0L &&
            System.nanoTime() - updatedTime < refreshTime
        ) {
            fetchFromDatabase()
        } else {
            fetchFromRemote()
        }
    }

    fun refreshFromRemote() {
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val items = ItemDatabase(getApplication()).itemDao().getAllItems()
            processResponse(items)
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            itemService.getDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<ItemBreed>>() {
                    override fun onSuccess(t: List<ItemBreed>) {
                        saveItemsLocally(t)
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        loadError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun processResponse(items: List<ItemBreed>) {
        itemList.postValue(items)
        loading.value = false
        loadError.value = false
    }

    private fun saveItemsLocally(items: List<ItemBreed>) {
        launch {
            val dao = ItemDatabase(getApplication()).itemDao()
            dao.deleteAll()

            val result = dao.insertAll(*items.toTypedArray())
            var i = 0

            while (i < items.size) {
                items[i].uuid = result[i].toInt()
                i++
            }
            processResponse(items)
        }

        sharePreferenceUtil.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        loading.value = true
        disposable.clear()
    }
}