package com.frontendmasters.campusoverflow.api.utils

sealed class ApiError : Throwable() {
    data class NetworkError(override val message: String) : ApiError()
    data class AuthError(override val message: String) : ApiError()
    data class ServerError(override val message: String) : ApiError()
    data class UnknownError(override val message: String) : ApiError()
} 