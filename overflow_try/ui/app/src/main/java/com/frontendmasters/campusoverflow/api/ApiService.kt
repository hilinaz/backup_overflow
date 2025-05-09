package com.frontendmasters.campusoverflow.api

import com.frontendmasters.campusoverflow.api.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // User endpoints
    @POST("user/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<User>>
    
    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<User>>
    
    @GET("user/check")
    suspend fun checkUser(): Response<ApiResponse<User>>
    
    @GET("user/getFullName")
    suspend fun getFullName(): Response<ApiResponse<String>>
    
    @GET("user/getUserStats")
    suspend fun getUserStats(): Response<ApiResponse<UserStats>>
    
    @GET("user/getAllUserNamesAndProfessions")
    suspend fun getAllUserNamesAndProfessions(): Response<ApiResponse<List<User>>>
    
    // Question endpoints
    @POST("question")
    suspend fun createQuestion(@Body request: CreateQuestionRequest): Response<ApiResponse<Question>>
    
    @GET("question")
    suspend fun getAllQuestions(): Response<ApiResponse<List<Question>>>
    
    @GET("question/countQuestions")
    suspend fun countQuestions(): Response<ApiResponse<Int>>
    
    @GET("question/{question_id}")
    suspend fun getSingleQuestion(@Path("question_id") questionId: String): Response<ApiResponse<Question>>
    
    @GET("question/search/{search}")
    suspend fun searchQuestions(@Path("search") searchQuery: String): Response<ApiResponse<List<Question>>>
    
    @DELETE("question/{questionid}")
    suspend fun deleteQuestion(@Path("questionid") questionId: String): Response<ApiResponse<Unit>>
    
    // Answer endpoints
    @POST("answer")
    suspend fun postAnswer(@Body answer: String): Response<ApiResponse<Answer>>
    
    @GET("answer/getAnswerStats")
    suspend fun getAnswerStats(): Response<ApiResponse<AnswerStats>>
    
    @GET("answer/{questionid}")
    suspend fun getAnswersForQuestion(@Path("questionid") questionId: String): Response<ApiResponse<List<Answer>>>
} 