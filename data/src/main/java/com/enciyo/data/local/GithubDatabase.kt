package com.enciyo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.enciyo.data.local.dao.FavoriteDao
import com.enciyo.data.local.dao.UserDao
import com.enciyo.data.local.dao.UserDetailDao
import com.enciyo.data.local.entity.FavoriteEntity
import com.enciyo.data.local.entity.UserDetailEntity
import com.enciyo.data.local.entity.UsersEntity

@Database(
    entities = [UsersEntity::class, FavoriteEntity::class, UserDetailEntity::class],
    version = 4
)
abstract class GithubDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val favoriteDao: FavoriteDao
    abstract val userDetailDao: UserDetailDao
}