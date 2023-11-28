package com.enciyo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0,
    val username: String,
    val imageUrl: String
)