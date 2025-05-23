package com.frontendmasters.campusoverflow.api.models

data class Answer(
    val _id: String,
    val answer: String,
    val questionId: String,
    val userId: String,
    val createdAt: String
)

data class AnswerStats(
    val totalAnswers: Int
)

data class PostAnswerRequest(
    val answer: String,
    val questionid: String,
    val userid: String
) 