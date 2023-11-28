package com.enciyo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.enciyo.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insert(vararg user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Query("DELETE FROM UserEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM UserEntity")
    suspend fun getUsers(): List<UserEntity>

}
