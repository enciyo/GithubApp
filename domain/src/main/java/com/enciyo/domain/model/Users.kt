package com.enciyo.domain.model

data class Users(
    val users: List<User>,
    val currentPage: Int,
    val nextPage: Int?
)