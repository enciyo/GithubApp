package com.enciyo.domain


data class User(
    val id: Long,
    val username: String,
    val imageUrl: String,
    val isFavorite: Boolean
)


data class Users(
    val users: List<User>,
    val currentPage:Int,
    val nextPage: Int?
)

