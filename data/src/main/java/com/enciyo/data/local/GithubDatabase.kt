package com.enciyo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enciyo.data.local.dao.FavoriteDao
import com.enciyo.data.local.dao.UserDao
import com.enciyo.data.local.entity.FavoriteEntity
import com.enciyo.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, FavoriteEntity::class], version = 2)
abstract class GithubDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val favoriteDao: FavoriteDao
}