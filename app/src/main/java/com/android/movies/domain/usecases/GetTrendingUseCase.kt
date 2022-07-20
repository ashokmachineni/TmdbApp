package com.android.movies.domain.usecases

import com.android.movies.domain.repositories.MoviesRepository
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    fun getMoviesResultStream() = moviesRepository.getMoviesResultStream()

}