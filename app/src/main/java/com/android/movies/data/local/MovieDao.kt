package com.android.movies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.android.movies.domain.models.trending.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveTrendingMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie")
    fun getTrendingMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie ORDER BY movie.title ASC")
    fun sortByNameASC(): Flow<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY movie.title DESC")
    fun sortByNameDESC(): Flow<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY movie.vote_average ASC")
    fun sortByRatingASC(): Flow<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY movie.vote_average DESC")
    fun sortByRatingDESC(): Flow<List<Movie>>

}