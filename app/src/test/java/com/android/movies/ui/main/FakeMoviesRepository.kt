package com.android.movies.ui.main

import androidx.paging.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.movies.data.MoviesRemoteMediator
import com.android.movies.data.local.AppDatabase
import com.android.movies.data.repositories.MoviesRepositoryImpl
import com.android.movies.domain.models.ApiResponse
import com.android.movies.domain.models.SortDirection
import com.android.movies.domain.models.trending.Movie
import com.android.movies.domain.models.trending.TrendingResponse
import com.android.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.runner.RunWith
import org.mockito.Mockito

class FakeMoviesRepository(private val appDatabase: AppDatabase) : MoviesRepository {

    companion object {
        val movies = listOf(
            Movie(id = 1, title = "movie 1"),
            Movie(id = 2, title = "movie 2"),
            Movie(id = 3, title = "movie 3"),
        )

        val fakeResponse = ApiResponse(
            success = true,
            statusCode = 200,
            statusMessage = "success",
            data = TrendingResponse(movies = movies)
        )

    }


    override suspend fun getTrendingLiveData(page: Int): ApiResponse<TrendingResponse> {
        return fakeResponse
    }

    @ExperimentalPagingApi
    override fun getMoviesResultStream(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 3
            ),
            remoteMediator = MoviesRemoteMediator(this, appDatabase),
            pagingSourceFactory = { appDatabase.getMovieDao().getTrendingMovies() },
        ).flow
    }

    override fun sortByName(direction: SortDirection) = flow {
        emit(emptyList<Movie>())
    }

    override fun sortByRating(direction: SortDirection) = flow {
        emit(emptyList<Movie>())
    }


}