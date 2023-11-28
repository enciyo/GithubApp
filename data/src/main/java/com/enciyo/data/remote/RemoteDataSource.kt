package com.enciyo.data.remote

import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users

interface RemoteDataSource {

    suspend fun searchUser(keyword: String, page: Int = 1): Result<Users>
    suspend fun getUserDetail(username: String): Result<UserDetail>
}

