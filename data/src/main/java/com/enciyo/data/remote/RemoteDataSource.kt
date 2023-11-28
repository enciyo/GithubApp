package com.enciyo.data.remote

import com.enciyo.domain.Users

interface RemoteDataSource {

    suspend fun searchUser(keyword: String, page: Int = 1): Result<Users>
}

