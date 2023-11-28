package com.enciyo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.enciyo.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

    @Insert
    suspend fun insert(vararg value: FavoriteEntity)

    @Delete
    suspend fun delete(value: FavoriteEntity)

    @Update
    suspend fun update(value: FavoriteEntity)

    @Query("select * from FavoriteEntity")
    fun getAll(): Flow<List<FavoriteEntity>>


}