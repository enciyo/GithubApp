package com.enciyo.domain.model

data class UserDetail(
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