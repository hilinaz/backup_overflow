package com.frontendmasters.campusoverflow.api.repository

import com.frontendmasters.campusoverflow.api.ApiClient
import com.frontendmasters.campusoverflow.api.models.*

class ApiRepository : BaseRepository() {
    private val apiService = ApiClient.apiService
    
    suspend fun login(email: String, password: String): Result<User> {
        return safeApiCall { apiService.login(LoginRequest(email, password)) }
    }
    
    suspend fun register(fullName: String, email: String, password: String, profession: String): Result<User> {
        return safeApiCall { apiService.register(RegisterRequest(fullName, email, password, profession)) }
    }
    
    suspend fun getAllQuestions(): Result<List<Question>> {
        return safeApiCall { apiService.getAllQuestions() }
    }
    
    suspend fun createQuestion(title: String, description: String): Result<Question> {
        return safeApiCall { apiService.createQuestion(CreateQuestionRequest(title, description)) }
    }
    
    suspend fun getAnswersForQuestion(questionId: String): Result<List<Answer>> {
        return safeApiCall { apiService.getAnswersForQuestion(questionId) }
    }
    
    suspend fun getUserStats(): Result<UserStats> {
        return safeApiCall { apiService.getUserStats() }
    }
    
    suspend fun getAnswerStats(): Result<AnswerStats> {
        return safeApiCall { apiService.getAnswerStats() }
    }
} 