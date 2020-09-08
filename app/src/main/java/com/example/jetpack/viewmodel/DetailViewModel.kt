package com.example.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetpack.model.ItemBreed

class DetailViewModel : ViewModel() {

    val currentItem = MutableLiveData<ItemBreed>()

    fun getItemBreed(itemId: Int) {
        val itemOne = ItemBreed("1", "test one", "test one", "test one")
        val itemTwo = ItemBreed("2", "test two", "test two", "test two")
        val testList = arrayListOf<ItemBreed>(itemOne, itemTwo)

        val item = testList.first { itemBreed -> itemBreed.itemId.equals(itemId.toString()) }
        currentItem.postValue(item)
    }
}