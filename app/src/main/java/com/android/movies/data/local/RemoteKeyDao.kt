package com.android.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(movieKey: MovieKey)

    @Query("SELECT * FROM movieKey ORDER BY id DESC")
    suspend fun getMovieKey(): MovieKey?


}