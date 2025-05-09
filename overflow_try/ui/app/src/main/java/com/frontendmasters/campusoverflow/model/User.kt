package com.frontendmasters.campusoverflow.model

enum class UserRole {
    STUDENT,
    TEACHER,
    ADMIN
}

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val role: UserRole
) 