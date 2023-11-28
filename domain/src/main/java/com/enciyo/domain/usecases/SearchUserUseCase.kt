package com.enciyo.domain.usecases

import com.enciyo.domain.Repository
import com.enciyo.domain.applyFavorites
import com.enciyo.shared.onMapSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SearchUserUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(page: Int, username: String) =
        repository.getUsers(page, username)
            .onMapSuccess { it ->
                it.applyFavorites(repository.getFavorites().map { it.getOrNull()?.users.orEmpty() }
                    .first())
            }

}

