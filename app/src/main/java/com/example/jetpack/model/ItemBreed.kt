package com.example.jetpack.model

import com.google.gson.annotations.SerializedName

data class ItemBreed(
    @SerializedName("id")
    val itemId: String?,
    @SerializedName("name")
    val ItemName: String?,
    @SerializedName("life_span")
    val ItemLifeSpan: String?,
    @SerializedName("url")
    val ItemImageUrl: String?
)