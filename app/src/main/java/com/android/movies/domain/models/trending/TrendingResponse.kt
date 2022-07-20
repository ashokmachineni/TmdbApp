package com.android.movies.domain.models.trending

import com.google.gson.annotations.SerializedName


data class TrendingResponse(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val movies: List<Movie>? = null,
    @SerializedName("totalPages")
    val totalPages: Int? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null
)