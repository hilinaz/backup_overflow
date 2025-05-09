package com.frontendmasters.campusoverflow.api.repository

import com.frontendmasters.campusoverflow.api.ApiResponse
import com.frontendmasters.campusoverflow.api.utils.ApiError
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<ApiResponse<T>>): Result<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    Result.success(it)
                } ?: Result.failure(ApiError.UnknownError("No data received"))
            } else {
                when (response.code()) {
                    401 -> Result.failure(ApiError.AuthError("Unauthorized"))
                    404 -> Result.failure(ApiError.ServerError(404, "Not found"))
                    500 -> Result.failure(ApiError.ServerError(500, "Server error"))
                    else -> Result.failure(ApiError.UnknownError("API call failed: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(ApiError.NetworkError(e.message ?: "Network error occurred"))
        }
    }
} 