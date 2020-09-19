package com.example.jetpack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ItemBreed(

    @ColumnInfo(name = "item_id")
    @SerializedName("id")
    val itemId: String?,

    @ColumnInfo(name = "item_name")
    @SerializedName("name")
    val ItemName: String?,

    @ColumnInfo(name = "item_life_span")
    @SerializedName("life_span")
    val ItemLifeSpan: String?,

    @ColumnInfo(name = "item_url")
    @SerializedName("url")
    val ItemImageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}