package com.enciyo.data.remote

import com.enciyo.data.remote.model.SearchResponse
import com.enciyo.data.remote.model.UserItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") keyword: String,
        @Query("page") page: Int,
        @Query("per_page") perPage:Int
    ): Result<SearchResponse>

    @GET("/users/{username}")
    suspend fun getUserByUsername(@Path("username") login: String): Result<UserItem>
}