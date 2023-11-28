package com.enciyo.data.local

import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertUsers(user: Users): Result<Unit>
    suspend fun getUsers(): Result<Users>
    suspend fun deleteAllUsers(): Result<Unit>
    suspend fun favoriteTransaction(user: User, isAdded:Boolean) : Result<Unit>
    fun getFavorites() : Flow<Result<Users>>
    suspend fun favoriteTransaction(user: UserDetail, isAdded: Boolean): Result<Unit>
}

