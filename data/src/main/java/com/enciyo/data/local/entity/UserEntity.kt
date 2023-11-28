package com.enciyo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val username: String,
    val imageUrl: String
)