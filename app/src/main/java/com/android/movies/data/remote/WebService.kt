package com.android.movies.data.remote

import com.android.movies.BuildConfig
import com.android.movies.domain.models.trending.TrendingResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


@JvmSuppressWildcards
interface WebService {


    @GET("trending/all/day")
    suspend fun getTrending(
        @Query("page") page: Int = 1,
        @Query("api_key") api_key: String = ApiConstants.API_KEY,
    ): Response<TrendingResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: String,
        @Query("api_key") api_key: String = ApiConstants.API_KEY,
    ): Response<TrendingResponse>


    companion object {

        operator fun invoke(headerInterceptor: HeaderInterceptor): WebService {

            val okHttpClint = OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(headerInterceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .build()

            return Retrofit.Builder()
                .client(okHttpClint)
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService::class.java)
        }
    }

}

