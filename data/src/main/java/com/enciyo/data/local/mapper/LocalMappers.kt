package com.enciyo.data.local.mapper

import com.enciyo.data.local.entity.FavoriteEntity
import com.enciyo.data.local.entity.UserDetailEntity
import com.enciyo.data.local.entity.UsersEntity
import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail

fun User.toUserEntity() = UsersEntity(id, username, imageUrl)
fun User.toFavoriteEntity() = FavoriteEntity(id, username, imageUrl)
fun UserDetail.toFavoriteEntity() = FavoriteEntity(id, username, imageUrl)

fun UsersEntity.toUser() = User(id, username, imageUrl, isFavorite = false)
fun FavoriteEntity.toUser() = User(id, username, imageUrl, isFavorite = true)


fun UserDetail.toUserDetailEntity() = UserDetailEntity(
    id,
    username,
    imageUrl,
    shareLink,
    isFavorite,
    name,
    company,
    blogUrl,
    bio,
    twitterUsername,
    reposCount,
    gistsCount,
    createdTime,
    updatedTime,
    followingCount,
    followersCount
)




fun UserDetailEntity.toUserDetailEntity() = UserDetail(
    id,
    username,
    imageUrl,
    shareLink,
    isFavorite,
    name,
    company,
    blogUrl,
    bio,
    twitterUsername,
    reposCount,
    gistsCount,
    createdTime,
    updatedTime,
    followingCount,
    followersCount
)


