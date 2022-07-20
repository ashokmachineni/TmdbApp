package com.android.movies.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.movies.data.MoviesRemoteMediator
import com.android.movies.data.local.AppDatabase
import com.android.movies.data.remote.WebService
import com.android.movies.data.remote.apiRequest
import com.android.movies.domain.models.ApiResponse
import com.android.movies.domain.models.SortDirection
import com.android.movies.domain.models.trending.Movie
import com.android.movies.domain.models.trending.TrendingResponse
import com.android.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val webService: WebService,
    private val appDatabase: AppDatabase
) : MoviesRepository {


    override suspend fun getTrendingLiveData(page: Int): ApiResponse<TrendingResponse> {

        return apiRequest { webService.getTrending(page) }

    }


    /**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */
    @ExperimentalPagingApi
    override fun getMoviesResultStream(): Flow<PagingData<Movie>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = MoviesRemoteMediator(this, appDatabase),
            pagingSourceFactory = { appDatabase.getMovieDao().getTrendingMovies() },
        ).flow
    }

    override fun sortByName(direction: SortDirection) = when (direction) {
        SortDirection.ASC -> appDatabase.getMovieDao().sortByNameASC()
        SortDirection.DESC -> appDatabase.getMovieDao().sortByNameDESC()
    }

    override fun sortByRating(direction: SortDirection) = when (direction) {
        SortDirection.ASC -> appDatabase.getMovieDao().sortByRatingASC()
        SortDirection.DESC -> appDatabase.getMovieDao().sortByRatingDESC()
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

}