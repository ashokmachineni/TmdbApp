package com.android.movies.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase

    private lateinit var movieDao: MovieDao
    private lateinit var movieKeyDao: RemoteKeyDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        movieDao = db.getMovieDao()
        movieKeyDao = db.getMovieKeyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun testcase1() = runBlocking {

        // Given
        val movieKey = MovieKey(id = 1, nextKey = 5)


        // When
        movieKeyDao.insertOrReplace(movieKey)


        // Then
        val actual = movieKeyDao.getMovieKey()
        assertThat(actual?.nextKey).isEqualTo(movieKey.nextKey)

    }

}