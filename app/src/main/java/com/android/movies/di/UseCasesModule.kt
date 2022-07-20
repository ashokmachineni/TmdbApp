package com.android.movies.di

import com.android.movies.domain.repositories.MoviesRepository
import com.android.movies.domain.usecases.GetTrendingUseCase
import com.android.movies.domain.usecases.SortResultsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    fun provideGetTrendingUseCase(moviesRepository: MoviesRepository) =
        GetTrendingUseCase(moviesRepository)

    @Provides
    fun provideSortResultsUseCase(moviesRepository: MoviesRepository) =
        SortResultsUseCase(moviesRepository)


}