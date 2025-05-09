package com.frontendmasters.campusoverflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontendmasters.campusoverflow.ui.components.*
import com.frontendmasters.campusoverflow.viewmodel.QuestionViewModel

@Composable
fun CreateQuestionScreen(
    navController: NavController,
    viewModel: QuestionViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val questionState by viewModel.questionState.collectAsState()
    
    LaunchedEffect(questionState) {
        when (questionState) {
            is UiState.Success -> {
                if ((questionState as UiState.Success<Question>).data != null) {
                    navController.navigateUp()
                }
            }
            is UiState.Error -> {
                showError = true
                errorMessage = (questionState as UiState.Error).message
            }
            else -> {}
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ask a Question") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                minLines = 5
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (title.isBlank() || description.isBlank()) {
                        showError = true
                        errorMessage = "Please fill in all fields"
                        return@Button
                    }
                    viewModel.createQuestion(title, description)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Post Question")
            }
        }
    }
    
    if (questionState is UiState.Loading) {
        LoadingIndicator()
    }
    
    if (showError) {
        ErrorDialog(
            message = errorMessage,
            onDismiss = { showError = false }
        )
    }
} 