package com.enciyo.data.remote.mapper

import com.enciyo.data.remote.PER_PAGE
import com.enciyo.data.remote.model.SearchItemResponse
import com.enciyo.data.remote.model.SearchResponse
import com.enciyo.domain.User
import com.enciyo.domain.Users

fun SearchResponse.toUsers(
    page: Int
): Users {
    val totalPage = totalCount / PER_PAGE
    val nextPage = if (totalPage != 0) page + 1 else null
    return Users(
        users = items.map(SearchItemResponse::toUser),
        nextPage = nextPage,
        currentPage = page
    )
}

fun SearchItemResponse.toUser() = User(
    id = id,
    username = this.login,
    imageUrl = this.avatarUrl,
    isFavorite = false
)