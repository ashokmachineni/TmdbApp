package com.android.movies.ui.main.details

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.android.movies.R
import com.android.movies.domain.models.trending.Movie
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@MediumTest
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailsFragmentTest {

    private var movie = Movie(
        title = "title1",
        releaseDate = "11/05/2021",
        overview = "any dummy overview",
        origin_country = listOf("Egypt")
    )

    @Before
    fun setUp() {

        launchFragmentInHiltContainer<MovieDetailsFragment>(
            themeResId = R.style.Theme_AppTask,
            fragmentArgs = bundleOf(
                "movieJson" to Gson().toJson(movie),
            )
        ) {

        }

    }


    @Test
    fun checkMovieNameDisplayed() {

        onView(withId(R.id.title)).check(matches(withText(movie.title)))

    }

    @Test
    fun checkReleaseDateDisplayed() {

        onView(withId(R.id.date)).check(matches(withText(movie.releaseDate)))

    }

    @Test
    fun checkOverviewDisplayed() {

        onView(withId(R.id.overview)).check(matches(withText(movie.overview)))

    }

    @Test
    fun checkCountryDisplayed() {

        onView(withId(R.id.country)).check(matches(withText("Country: ${movie.origin_country?:"Unknown"}")))

    }


}