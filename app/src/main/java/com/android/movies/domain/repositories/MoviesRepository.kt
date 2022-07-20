package com.android.movies.domain.repositories

import androidx.paging.PagingData
import com.android.movies.domain.models.ApiResponse
import com.android.movies.domain.models.SortDirection
import com.android.movies.domain.models.trending.Movie
import com.android.movies.domain.models.trending.TrendingResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getTrendingLiveData(page: Int = 1): ApiResponse<TrendingResponse>

    fun getMoviesResultStream(): Flow<PagingData<Movie>>

    fun sortByName(direction: SortDirection = SortDirection.DESC): Flow<List<Movie>>

    fun sortByRating(direction: SortDirection = SortDirection.DESC): Flow<List<Movie>>

}