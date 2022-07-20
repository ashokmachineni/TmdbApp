package com.android.movies.ui.main

import androidx.lifecycle.ViewModel
import com.android.movies.domain.usecases.GetTrendingUseCase
import com.android.movies.domain.usecases.SortResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getTrendingUseCase: GetTrendingUseCase,
    private val sortResultsUseCase: SortResultsUseCase,
) : ViewModel() {

    fun getTrending() = getTrendingUseCase.getMoviesResultStream()

    fun sortResults() = sortResultsUseCase

}