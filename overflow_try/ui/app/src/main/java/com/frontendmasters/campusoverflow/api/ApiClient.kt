package com.frontendmasters.campusoverflow.api

import android.content.Context
import com.frontendmasters.campusoverflow.api.utils.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:3000/" // Android emulator localhost
    private lateinit var tokenManager: TokenManager
    
    fun initialize(context: Context) {
        tokenManager = TokenManager(context)
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val authInterceptor = { chain: okhttp3.Interceptor.Chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${getStoredToken()}")
            .build()
        chain.proceed(request)
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val apiService: ApiService = retrofit.create(ApiService::class.java)
    
    private fun getStoredToken(): String {
        return tokenManager.getToken() ?: ""
    }
    
    fun saveToken(token: String) {
        tokenManager.saveToken(token)
    }
    
    fun clearToken() {
        tokenManager.clearToken()
    }
} 