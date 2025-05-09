package com.frontendmasters.campusoverflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontendmasters.campusoverflow.model.User
import com.frontendmasters.campusoverflow.model.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Initial)
    val signInState: StateFlow<SignInState> = _signInState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            try {
                // TODO: Implement actual sign in logic with your API
                // For now, using mock data
                val user = User(
                    id = 1,
                    firstName = "John",
                    lastName = "Doe",
                    username = "johndoe",
                    email = email,
                    role = UserRole.STUDENT
                )
                _signInState.value = SignInState.Success(user)
            } catch (e: Exception) {
                _signInState.value = SignInState.Error(e.message ?: "Sign in failed")
            }
        }
    }
}

sealed class SignInState {
    object Initial : SignInState()
    object Loading : SignInState()
    data class Success(val user: User) : SignInState()
    data class Error(val message: String) : SignInState()
} 