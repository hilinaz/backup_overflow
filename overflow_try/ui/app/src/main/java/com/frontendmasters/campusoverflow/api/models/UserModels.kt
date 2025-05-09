package com.frontendmasters.campusoverflow.api.models

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val profession: String,
    val role: String = "USER"
)

data class UserStats(
    val totalQuestions: Int,
    val totalAnswers: Int
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val profession: String
) 