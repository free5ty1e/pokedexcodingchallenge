package com.example.kotlin.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseSafeRepository() {

    // Centralized wrapper to safely call network APIs
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResource<T> {

        // Returns API response wrapped in NetworkResource class
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("SafeApiCall", "api call success!  response.body is $body")
                    NetworkResource.Success(data = body)
                } else {
                    Log.d("SafeApiCall", "api call failure! Success status was ${response.isSuccessful},  response.body was $body")
                    NetworkResource.Error(errorMessage = response.errorBody()?.string() ?: "Something went wrong")
                }
            } catch (e: HttpException) {
                Log.e("SafeApiCall", "api call failure! HttpException was ${e.message}")
                NetworkResource.Error(errorMessage = e.message ?: "Something went wrong: ${e.message}")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in NetworkResource.Error
                Log.d("SafeApiCall", "api call failure! IOException was ${e.message}")
                NetworkResource.Error("Please check your network connection: ${e.message}")
            } catch (e: Exception) {    // Fallback catch-all
                Log.d("SafeApiCall", "api call failure! Generic Exception was ${e.message}")
                NetworkResource.Error(errorMessage = "Something went wrong: ${e.message}")
            }
        }
    }
}
