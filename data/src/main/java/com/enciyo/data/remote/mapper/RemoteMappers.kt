package com.enciyo.data.remote.mapper

import com.enciyo.data.remote.PER_PAGE
import com.enciyo.data.remote.model.SearchItemResponse
import com.enciyo.data.remote.model.SearchResponse
import com.enciyo.data.remote.model.UserItem
import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users

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
    id = this.id,
    username = this.login,
    imageUrl = this.avatarUrl,
    isFavorite = false
)


fun UserItem.toUserDetail() = UserDetail(
    id = this.id,
    username = this.login,
    imageUrl = this.avatarUrl,
    shareLink = this.htmlUrl,
    isFavorite = false,
    name = this.name,
    company = this.company,
    blogUrl = blog,
    bio = bio,
    twitterUsername = twitterUsername,
    reposCount = publicRepos,
    gistsCount = publicGists,
    createdTime = createdAt,
    updatedTime = updatedAt,
    followersCount = followers,
    followingCount = following
)