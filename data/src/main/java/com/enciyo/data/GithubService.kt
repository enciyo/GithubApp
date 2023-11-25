package com.enciyo.data

import retrofit2.http.GET

interface GithubService {

    @GET("")
    suspend fun get() : Unit
}