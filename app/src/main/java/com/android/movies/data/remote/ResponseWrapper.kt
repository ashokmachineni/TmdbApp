package com.android.movies.data.remote

import com.android.movies.domain.models.ApiResponse
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException


const val ERROR_NO_INTERNET_CONNECTION = "no_internet_connection"
const val ERROR_TIMEOUT = "timeout"


suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): ApiResponse<T> {

    try {

        val response = call.invoke()

        val sentTimestamp = response.raw().sentRequestAtMillis
        val receivedTimestamp = response.raw().receivedResponseAtMillis

        val requestTime = receivedTimestamp - sentTimestamp

        if (response.isSuccessful) {


            return ApiResponse(
                response.isSuccessful, response.code(),
                response.message(),
                response.body(),
                requestTime
            )

        } else {

            val responseBody = JSONObject(response.errorBody()?.string().toString())

            val errorMessage = responseBody.getString("message")


            return ApiResponse(response.isSuccessful, response.code(), errorMessage, null, requestTime)
        }

    } catch (e: Exception) {
        e.printStackTrace()

        return when (e) {

            is UnknownHostException -> {
                ApiResponse(false, 0, ERROR_NO_INTERNET_CONNECTION, null)
            }

            is SocketTimeoutException -> {
                ApiResponse(false, 0, ERROR_TIMEOUT, null)
            }

            else -> {
                ApiResponse(false, 0, e.message.toString(), null)
            }

        }//when

    }//catch

}