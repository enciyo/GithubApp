package com.enciyo.data.local

import com.enciyo.domain.User
import com.enciyo.domain.Users
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertUsers(user: Users): Result<Unit>
    suspend fun getUsers(): Result<Users>
    suspend fun deleteAllUsers(): Result<Unit>
    suspend fun favoriteTransaction(user: User, isAdded:Boolean) : Result<Unit>
    fun getFavorites() : Flow<Result<Users>>
}

