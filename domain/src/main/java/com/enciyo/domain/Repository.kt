package com.enciyo.domain

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getUsers(page: Int = 1, username: String): Flow<Result<Users>>
    fun favoriteTransactions(user: User, isAdded: Boolean): Flow<Result<Unit>>
    fun getFavorites(): Flow<Result<Users>>
}