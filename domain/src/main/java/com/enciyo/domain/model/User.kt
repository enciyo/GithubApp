package com.enciyo.domain.model


data class User(
    val id: Long,
    val username: String,
    val imageUrl: String,
    val isFavorite: Boolean
)


