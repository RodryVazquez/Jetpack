package com.example.jetpack.model

import io.reactivex.Single
import retrofit2.http.GET

interface ItemApi {
    @GET("/DevTides/DogsApi/master/dogs.json")
    fun getBreedItems(): Single<List<ItemBreed>>
}