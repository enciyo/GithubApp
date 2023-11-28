package com.enciyo.domain

import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getUsers(page: Int = 1, username: String): Flow<Result<Users>>
    fun favoriteTransactions(user: User, isAdded: Boolean): Flow<Result<Unit>>
    fun getFavorites(): Flow<Result<Users>>
    fun getUsersDetail(username: String): Flow<Result<UserDetail>>
    fun favoriteTransactions(user: UserDetail, isAdded: Boolean): Flow<Result<Unit>>
}