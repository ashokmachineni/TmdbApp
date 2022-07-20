package com.android.movies.domain.models.trending

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(

    @PrimaryKey
    val id: Int? = null,

    val adult: Boolean? = null,

    val backdropPath: String? = null,

    val genreIds: List<Int>? = null,

    val originalLanguage: String? = null,

    val originalTitle: String? = null,

    val overview: String? = null,

    val poster_path: String? = null,

    val releaseDate: String? = null,

    val title: String? = null,

    val video: Boolean? = null,

    val vote_average: Double? = null,

    val vote_count: Int? = null,

    val popularity: Double? = null,

    val media_type: String? = null,

    val originalName: String? = null,

    val origin_country: List<String>? = null,

    val name: String? = null,

    val firstAirDate: String? = null
)