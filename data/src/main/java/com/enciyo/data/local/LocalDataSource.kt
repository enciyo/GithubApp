package com.enciyo.data.local

import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertUsers(user: Users): Result<Unit>
    suspend fun favoriteTransaction(user: User, isAdded:Boolean) : Result<Unit>
    fun getFavorites() : Flow<Result<Users>>
    suspend fun favoriteTransaction(user: UserDetail, isAdded: Boolean): Result<Unit>
    fun getUsersBy(username: String, page: Int, limit: Int): Flow<Result<Users>>
    suspend fun insertUserDetail(user: UserDetail): Result<Unit>
    fun getUserDetailById(username: String): Flow<Result<UserDetail>>
}

