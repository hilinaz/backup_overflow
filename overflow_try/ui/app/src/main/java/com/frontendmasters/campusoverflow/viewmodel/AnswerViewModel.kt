package com.frontendmasters.campusoverflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontendmasters.campusoverflow.api.models.Answer
import com.frontendmasters.campusoverflow.api.repository.ApiRepository
import com.frontendmasters.campusoverflow.api.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnswerViewModel : ViewModel() {
    private val repository = ApiRepository()
    
    private val _answersState = MutableStateFlow<UiState<List<Answer>>>(UiState.Success(emptyList()))
    val answersState: StateFlow<UiState<List<Answer>>> = _answersState.asStateFlow()
    
    private val _answerStatsState = MutableStateFlow<UiState<Int>>(UiState.Success(0))
    val answerStatsState: StateFlow<UiState<Int>> = _answerStatsState.asStateFlow()
    
    fun getAnswersForQuestion(questionId: String) {
        viewModelScope.launch {
            _answersState.value = UiState.Loading
            try {
                val result = repository.getAnswersForQuestion(questionId)
                result.onSuccess { answers ->
                    _answersState.value = UiState.Success(answers)
                }.onFailure { error ->
                    _answersState.value = UiState.Error(error.message ?: "Failed to load answers")
                }
            } catch (e: Exception) {
                _answersState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun getAnswerStats() {
        viewModelScope.launch {
            _answerStatsState.value = UiState.Loading
            try {
                val result = repository.getAnswerStats()
                result.onSuccess { stats ->
                    _answerStatsState.value = UiState.Success(stats.totalAnswers)
                }.onFailure { error ->
                    _answerStatsState.value = UiState.Error(error.message ?: "Failed to get answer stats")
                }
            } catch (e: Exception) {
                _answerStatsState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun postAnswer(questionId: String, answer: String) {
        viewModelScope.launch {
            _answersState.value = UiState.Loading
            try {
                val result = repository.postAnswer(answer)
                result.onSuccess { newAnswer ->
                    // Refresh answers list after posting
                    getAnswersForQuestion(questionId)
                }.onFailure { error ->
                    _answersState.value = UiState.Error(error.message ?: "Failed to post answer")
                }
            } catch (e: Exception) {
                _answersState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 