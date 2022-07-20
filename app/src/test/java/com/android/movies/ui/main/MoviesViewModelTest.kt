package com.android.movies.ui.main


import android.content.Context
import android.os.Looper.getMainLooper
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.room.Room
import androidx.room.withTransaction
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.movies.data.local.AppDatabase
import com.android.movies.domain.models.trending.Movie
import com.android.movies.domain.usecases.GetTrendingUseCase
import com.android.movies.domain.usecases.SortResultsUseCase
import com.android.movies.ui.main.movies.MoviesAdapter
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.LooperMode

@RunWith(AndroidJUnit4::class)
@LooperMode(LooperMode.Mode.PAUSED)
@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    private val mock = Mockito.mock(MoviesViewModel::class.java)
    private val testScope = TestScope()
    private val testDispatcher = StandardTestDispatcher(testScope.testScheduler)

    private lateinit var db: AppDatabase
    private lateinit var fakeMoviesRepository: FakeMoviesRepository
    private lateinit var getTrendingUseCase: GetTrendingUseCase
    private lateinit var sortResultsUseCase: SortResultsUseCase
    private lateinit var viewModel: MoviesViewModel
    private lateinit var fakeMoviesAdapter: MoviesAdapter


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).fallbackToDestructiveMigration()
            .build()
        fakeMoviesRepository = FakeMoviesRepository(db)
        fakeMoviesAdapter = MoviesAdapter()

        getTrendingUseCase = GetTrendingUseCase(fakeMoviesRepository)
        sortResultsUseCase = SortResultsUseCase(fakeMoviesRepository)
        viewModel = MoviesViewModel(getTrendingUseCase, sortResultsUseCase)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getMoviesCheckResponse(): Unit = testScope.runTest {
        val differ = AsyncPagingDataDiffer(
            diffCallback = MovieDiffCallback(),
            updateCallback = MovieListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        Mockito.`when`(mock.getTrending()).thenReturn(
            flowOf(
                PagingData.from(listOf(
                    Movie(id = 1, title = "movie 1"),
                    Movie(id = 2, title = "movie 2"),
                    Movie(id = 3, title = "movie 3"),
                ))
            )
        )
        mock.getTrending().take(1).collectLatest {
            differ.submitData(it)
        }


        advanceUntilIdle()
        shadowOf(getMainLooper()).idle()
        assertThat(
            differ.snapshot().first()
        ).isEqualTo(FakeMoviesRepository.fakeResponse.data?.movies?.get(0))
    }

}