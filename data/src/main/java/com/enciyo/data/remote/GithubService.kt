package com.enciyo.data.remote

import com.enciyo.data.remote.model.SearchResponse
import com.enciyo.data.remote.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") keyword: String,
        @Query("page") page: Int = 1
    ): Result<SearchResponse>

    @GET("/users/{username}")
    suspend fun getUserByUsername(@Path("username") login: String): Result<User>
}