package com.android.movies.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.movies.data.local.AppDatabase
import com.android.movies.data.local.MovieKey
import com.android.movies.domain.models.trending.Movie
import com.android.movies.domain.repositories.MoviesRepository
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator(
    private val moviesRepository: MoviesRepository,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

        //

        return try {
            val loadKey = when(loadType){
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND ->{
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    getMovieKey()
                }
            }


            val response = moviesRepository.getTrendingLiveData(loadKey?.nextKey?:1)


            val movies = response.data?.movies

            if (movies != null) {
                appDatabase.withTransaction {
                    appDatabase.getMovieKeyDao()
                        .insertOrReplace(MovieKey(0, (loadKey?.nextKey?:1)+1))
                    appDatabase.getMovieDao().saveTrendingMovies(movies)
                }

            }
            MediatorResult.Success(endOfPaginationReached = false)

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }

        //




    }

    private suspend fun getMovieKey(): MovieKey? {
        return appDatabase.getMovieKeyDao().getMovieKey()
    }

}