package com.enciyo.data.remote

import com.enciyo.data.remote.mapper.toUsers
import com.enciyo.domain.Users
import javax.inject.Inject


const val PER_PAGE = 30

class RemoteDataSourceImp @Inject constructor(
    private val service: GithubService
) : RemoteDataSource {

    override suspend fun searchUser(keyword: String, page: Int): Result<Users> =
        service.searchUsers(keyword, page, PER_PAGE)
            .map { it.toUsers(page) }

}
