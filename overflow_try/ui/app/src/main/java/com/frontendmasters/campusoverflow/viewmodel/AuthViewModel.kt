package com.frontendmasters.campusoverflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontendmasters.campusoverflow.api.models.User
import com.frontendmasters.campusoverflow.api.repository.ApiRepository
import com.frontendmasters.campusoverflow.api.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {
    private val _authState = MutableStateFlow<UiState<User>>(UiState.Success(null))
    val authState: StateFlow<UiState<User>> = _authState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            try {
                val result = repository.login(email, password)
                result.onSuccess { user ->
                    _authState.value = UiState.Success(user)
                }.onFailure { error ->
                    _authState.value = UiState.Error(error.message ?: "Login failed")
                }
            } catch (e: Exception) {
                _authState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun register(fullName: String, email: String, password: String, profession: String) {
        viewModelScope.launch {
            _authState.value = UiState.Loading
            try {
                val result = repository.register(fullName, email, password, profession)
                result.onSuccess { user ->
                    _authState.value = UiState.Success(user)
                }.onFailure { error ->
                    _authState.value = UiState.Error(error.message ?: "Registration failed")
                }
            } catch (e: Exception) {
                _authState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 