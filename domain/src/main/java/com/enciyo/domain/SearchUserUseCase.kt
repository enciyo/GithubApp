package com.enciyo.domain

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SearchUserUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(page: Int, username: String) =
        combine(
            repository.getUsers(page, username),
            repository.getFavorites()
                .map { it.getOrNull()?.users.orEmpty() },
            ::transform
        )

    private fun transform(
        users: Result<Users>,
        favorites: List<User>
    ) =
        users.map {
            it.copy(
                users = it.users.setFavorite(favorites)
            )
        }

    private fun List<User>.setFavorite(favorites: List<User>) = map { user ->
        user.copy(isFavorite = favorites.find { it.id == user.id } != null)
    }


}
