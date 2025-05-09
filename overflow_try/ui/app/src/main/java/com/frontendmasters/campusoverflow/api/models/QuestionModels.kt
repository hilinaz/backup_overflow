package com.frontendmasters.campusoverflow.api.models

data class Question(
    val _id: String,
    val title: String,
    val description: String,
    val userId: String,
    val author: String,
    val createdAt: Long,
    val hasAnswer: Boolean = false
)

data class CreateQuestionRequest(
    val title: String,
    val description: String
) 