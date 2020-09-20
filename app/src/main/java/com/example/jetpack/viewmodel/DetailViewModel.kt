package com.example.jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.jetpack.model.ItemBreed
import com.example.jetpack.model.persistence.ItemDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val currentItem = MutableLiveData<ItemBreed>()

    fun getItemBreed(uuid: Int) {
        launch {
            val dao = ItemDatabase(getApplication()).itemDao()
            val itemBread = dao.getItem(uuid)
            currentItem.value = itemBread
        }
    }
}