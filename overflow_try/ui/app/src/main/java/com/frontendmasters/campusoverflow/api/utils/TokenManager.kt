package com.frontendmasters.campusoverflow.api.utils

import android.content.Context

class TokenManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    
    fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }
    
    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }
    
    fun clearToken() {
        sharedPreferences.edit().remove("auth_token").apply()
    }
} 