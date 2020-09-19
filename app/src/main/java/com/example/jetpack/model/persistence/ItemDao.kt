package com.example.jetpack.model.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jetpack.model.ItemBreed

@Dao
interface ItemDao {
    @Insert
    suspend fun insertAll(vararg itemBreed: ItemBreed) : List<Long>

    @Query("SELECT * FROM itembreed")
    suspend fun getAllItems(): List<ItemBreed>

    @Query("SELECT * FROM itembreed WHERE uuid = :itemId")
    suspend fun getItem(itemId : Int): ItemBreed

    @Query("DELETE FROM itembreed")
    suspend fun deleteAll()
}