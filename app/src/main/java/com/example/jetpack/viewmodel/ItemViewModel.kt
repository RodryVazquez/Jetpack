package com.example.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpack.model.ItemApiService
import com.example.jetpack.model.ItemBreed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ItemViewModel : ViewModel() {

    private val itemService = ItemApiService()
    private val disposable = CompositeDisposable()

    val itemList = MutableLiveData<List<ItemBreed>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            itemService.getDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<ItemBreed>>() {
                    override fun onSuccess(t: List<ItemBreed>) {
                        itemList.value = t
                        loading.value = false
                        loadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        loadError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        loading.value = true
        disposable.clear()
    }
}