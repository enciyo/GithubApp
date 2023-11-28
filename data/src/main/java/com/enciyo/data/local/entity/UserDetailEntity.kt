package com.enciyo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserDetailEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val username: String,
    val imageUrl: String,
    val shareLink: String,
    val isFavorite: Boolean,
    val name: String,
    val company: String,
    val blogUrl: String,
    val bio: String,
    val twitterUsername: String,
    val reposCount: Int,
    val gistsCount: Int,
    val createdTime: String,
    val updatedTime: String,
    val followingCount: Int,
    val followersCount : Int
)