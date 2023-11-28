package com.enciyo.data.local.mapper

import com.enciyo.data.local.entity.FavoriteEntity
import com.enciyo.data.local.entity.UserEntity
import com.enciyo.domain.User

fun User.toUserEntity() = UserEntity(id, username, imageUrl)
fun User.toFavoriteEntity() = FavoriteEntity(id, username, imageUrl)

fun UserEntity.toUser() = User(id, username, imageUrl, isFavorite = false)
fun FavoriteEntity.toUser() = User(id, username, imageUrl, isFavorite = true)

