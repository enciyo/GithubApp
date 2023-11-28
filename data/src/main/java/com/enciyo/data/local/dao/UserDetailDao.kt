package com.enciyo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.enciyo.data.local.entity.UserDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailDao {

    @Insert
    suspend fun insert(vararg value: UserDetailEntity)

    @Delete
    suspend fun delete(value: UserDetailEntity)

    @Update
    suspend fun update(value: UserDetailEntity)

    @Query("select * from UserDetailEntity WHERE username = :username")
    fun getByUsername(username: String): Flow<UserDetailEntity>

}