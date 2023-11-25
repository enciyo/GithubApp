package com.enciyo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Sample::class], version = 1)
abstract class GithubDatabase : RoomDatabase()