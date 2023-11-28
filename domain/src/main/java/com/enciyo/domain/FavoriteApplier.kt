package com.enciyo.domain

import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun Users.applyFavorites(favorites: List<User>) = copy(users = users.applyFavorites(favorites))
suspend fun List<User>.applyFavorites(favorites: List<User>) = withContext(Dispatchers.Default){
    map { user ->
        user.copy(isFavorite = favorites.find { it.id == user.id } != null)
    }
}

suspend fun UserDetail.applyFavorites(favorites: List<User>) = withContext(Dispatchers.Default){
    copy(
        isFavorite = favorites.find { it.id == this@applyFavorites.id } != null
    )
}
