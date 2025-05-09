package com.frontendmasters.campusoverflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontendmasters.campusoverflow.api.models.User
import com.frontendmasters.campusoverflow.api.models.UserStats
import com.frontendmasters.campusoverflow.api.repository.ApiRepository
import com.frontendmasters.campusoverflow.api.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {
    private val repository = ApiRepository()
    
    private val _userState = MutableStateFlow<UiState<User>>(UiState.Success(null))
    val userState: StateFlow<UiState<User>> = _userState.asStateFlow()
    
    private val _userStatsState = MutableStateFlow<UiState<UserStats>>(UiState.Success(null))
    val userStatsState: StateFlow<UiState<UserStats>> = _userStatsState.asStateFlow()
    
    fun getUserProfile() {
        viewModelScope.launch {
            _userState.value = UiState.Loading
            try {
                val result = repository.checkUser()
                result.onSuccess { user ->
                    _userState.value = UiState.Success(user)
                }.onFailure { error ->
                    _userState.value = UiState.Error(error.message ?: "Failed to load user profile")
                }
            } catch (e: Exception) {
                _userState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun getUserStats() {
        viewModelScope.launch {
            _userStatsState.value = UiState.Loading
            try {
                val result = repository.getUserStats()
                result.onSuccess { stats ->
                    _userStatsState.value = UiState.Success(stats)
                }.onFailure { error ->
                    _userStatsState.value = UiState.Error(error.message ?: "Failed to load user stats")
                }
            } catch (e: Exception) {
                _userStatsState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun getFullName() {
        viewModelScope.launch {
            _userState.value = UiState.Loading
            try {
                val result = repository.getFullName()
                result.onSuccess { name ->
                    // Update user state with new name
                    (_userState.value as? UiState.Success)?.data?.let { user ->
                        _userState.value = UiState.Success(user.copy(fullName = name))
                    }
                }.onFailure { error ->
                    _userState.value = UiState.Error(error.message ?: "Failed to get full name")
                }
            } catch (e: Exception) {
                _userState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 