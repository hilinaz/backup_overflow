package com.frontendmasters.campusoverflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontendmasters.campusoverflow.api.models.Question
import com.frontendmasters.campusoverflow.api.repository.ApiRepository
import com.frontendmasters.campusoverflow.api.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {
    private val repository = ApiRepository()
    
    private val _questionsState = MutableStateFlow<UiState<List<Question>>>(UiState.Success(emptyList()))
    val questionsState: StateFlow<UiState<List<Question>>> = _questionsState.asStateFlow()
    
    private val _questionState = MutableStateFlow<UiState<Question>>(UiState.Success(null))
    val questionState: StateFlow<UiState<Question>> = _questionState.asStateFlow()
    
    private val _questionCountState = MutableStateFlow<UiState<Int>>(UiState.Success(0))
    val questionCountState: StateFlow<UiState<Int>> = _questionCountState.asStateFlow()
    
    fun getAllQuestions() {
        viewModelScope.launch {
            _questionsState.value = UiState.Loading
            try {
                val result = repository.getAllQuestions()
                result.onSuccess { questions ->
                    _questionsState.value = UiState.Success(questions)
                }.onFailure { error ->
                    _questionsState.value = UiState.Error(error.message ?: "Failed to load questions")
                }
            } catch (e: Exception) {
                _questionsState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun getQuestionById(questionId: String) {
        viewModelScope.launch {
            _questionState.value = UiState.Loading
            try {
                val result = repository.getSingleQuestion(questionId)
                result.onSuccess { question ->
                    _questionState.value = UiState.Success(question)
                }.onFailure { error ->
                    _questionState.value = UiState.Error(error.message ?: "Failed to load question")
                }
            } catch (e: Exception) {
                _questionState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun createQuestion(title: String, description: String) {
        viewModelScope.launch {
            _questionState.value = UiState.Loading
            try {
                val result = repository.createQuestion(title, description)
                result.onSuccess { question ->
                    _questionState.value = UiState.Success(question)
                }.onFailure { error ->
                    _questionState.value = UiState.Error(error.message ?: "Failed to create question")
                }
            } catch (e: Exception) {
                _questionState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
    
    fun getQuestionCount() {
        viewModelScope.launch {
            _questionCountState.value = UiState.Loading
            try {
                val result = repository.countQuestions()
                result.onSuccess { count ->
                    _questionCountState.value = UiState.Success(count)
                }.onFailure { error ->
                    _questionCountState.value = UiState.Error(error.message ?: "Failed to get question count")
                }
            } catch (e: Exception) {
                _questionCountState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
} 