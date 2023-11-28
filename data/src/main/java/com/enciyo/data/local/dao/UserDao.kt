package com.enciyo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.enciyo.data.local.entity.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg user: UsersEntity)

    @Delete
    suspend fun delete(user: UsersEntity)

    @Update
    suspend fun update(user: UsersEntity)

    @Query("DELETE FROM UsersEntity")
    suspend fun deleteAll()

    @Query("SELECT * from UsersEntity WHERE username  LIKE '%' || :username || '%' LIMIT (:page*:size)")
    fun getUsers(username: String, page: Int, size: Int): Flow<List<UsersEntity>>

    @Query("SELECT count(*) from UsersEntity WHERE username  LIKE '%' || :username || '%' LIMIT (:page*:size)")
    suspend fun totalCount(username: String, page: Int, size: Int): Int?


}
