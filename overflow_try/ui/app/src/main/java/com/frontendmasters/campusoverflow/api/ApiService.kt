package com.frontendmasters.campusoverflow.api

import com.frontendmasters.campusoverflow.api.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // User endpoints
    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<ApiResponse<User>>
    
    @POST("api/users/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<User>>
    
    @GET("api/users/check")
    suspend fun checkUser(): Response<ApiResponse<User>>
    
    @GET("api/users/getFullName")
    suspend fun getFullName(): Response<ApiResponse<String>>
    
    @GET("api/users/getUserStats")
    suspend fun getUserStats(): Response<ApiResponse<UserStats>>
    
    @GET("api/users/getAllUserNamesAndProfessions")
    suspend fun getAllUserNamesAndProfessions(): Response<ApiResponse<List<User>>>
    
    // Question endpoints
    @POST("api/question")
    suspend fun createQuestion(@Body request: CreateQuestionRequest): Response<ApiResponse<Question>>
    
    @GET("api/question")
    suspend fun getAllQuestions(): Response<ApiResponse<List<Question>>>
    
    @GET("api/question/countQuestions")
    suspend fun countQuestions(): Response<ApiResponse<Int>>
    
    @GET("api/question/{question_id}")
    suspend fun getSingleQuestion(@Path("question_id") questionId: String): Response<ApiResponse<Question>>
    
    @GET("api/question/search/{search}")
    suspend fun searchQuestions(@Path("search") searchQuery: String): Response<ApiResponse<List<Question>>>
    
    @DELETE("api/question/{questionid}")
    suspend fun deleteQuestion(@Path("questionid") questionId: String): Response<ApiResponse<Unit>>
    
    // Answer endpoints
    @POST("api/answer")
    suspend fun postAnswer(@Body request: PostAnswerRequest): Response<ApiResponse<Answer>>
    
    @GET("api/answer/getAnswerStats")
    suspend fun getAnswerStats(): Response<ApiResponse<AnswerStats>>
    
    @GET("api/answer/{questionid}")
    suspend fun getAnswersForQuestion(@Path("questionid") questionId: String): Response<ApiResponse<List<Answer>>>
} 