package com.enciyo.data.remote.model

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "total_count")
    val totalCount : Int,
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    @Json(name = "items")
    val items : List<SearchItemResponse>
)

