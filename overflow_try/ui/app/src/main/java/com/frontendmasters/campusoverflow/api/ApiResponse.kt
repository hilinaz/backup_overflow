package com.frontendmasters.campusoverflow.api

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
) 