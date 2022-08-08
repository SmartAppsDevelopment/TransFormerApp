package com.example.sofittasktranformer.network

import android.content.Context
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Umer Bilal
 * Created 7/23/2022 at 2:56 PM
 */
class BaseClientBuilder @Inject constructor(private val context: Context) {

    suspend fun <T : Any> safeApiCall(
        responseBack: suspend () -> Response<T>
    ): Result<T> {
        val callToService = responseBack.invoke()
        return if (callToService.isSuccessful) {
            Result.success(callToService.body()!!)
        } else {
            Result.failure(Exception("Error While Getting Data From Server"))
        }
    }
}