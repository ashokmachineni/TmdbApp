package com.android.movies.domain.usecases

import com.android.movies.domain.models.SortDirection
import com.android.movies.domain.repositories.MoviesRepository
import javax.inject.Inject
import javax.inject.Named


class SortResultsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    fun sortByName(direction: SortDirection = SortDirection.DESC) =
        moviesRepository.sortByName(direction)

    fun sortByRating(direction: SortDirection = SortDirection.DESC) =
        moviesRepository.sortByRating(direction)

}