package com.android.movies.di

import android.content.Context
import com.android.movies.data.local.AppDatabase
import com.android.movies.data.remote.HeaderInterceptor
import com.android.movies.data.remote.WebService
import com.android.movies.data.repositories.MoviesRepositoryImpl
import com.android.movies.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {

    @Provides
    fun provideWebService(): WebService {
        return WebService.invoke(HeaderInterceptor())
    }

    @Provides
    fun provideMoviesRepositoryImpl(
        webService: WebService,
        appDatabase: AppDatabase
    ): MoviesRepository {
        return MoviesRepositoryImpl(webService, appDatabase)
    }

    @Provides
    fun provideDB(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.invoke(context)
    }


}