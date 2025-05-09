package com.frontendmasters.campusoverflow.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontendmasters.campusoverflow.navigation.NavigationRoutes
import com.frontendmasters.campusoverflow.ui.components.*
import com.frontendmasters.campusoverflow.viewmodel.QuestionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun QuestionListScreen(
    navController: NavController,
    viewModel: QuestionViewModel = viewModel()
) {
    val questionsState by viewModel.questionsState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.getAllQuestions()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Questions") },
                actions = {
                    IconButton(onClick = { navController.navigate(NavigationRoutes.UserProfile.route) }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(NavigationRoutes.CreateQuestion.route) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Question")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (questionsState) {
                is UiState.Loading -> {
                    LoadingIndicator()
                }
                is UiState.Success -> {
                    val questions = (questionsState as UiState.Success<List<Question>>).data
                    if (questions.isEmpty()) {
                        EmptyStateView(
                            message = "No questions yet",
                            onAction = { navController.navigate(NavigationRoutes.CreateQuestion.route) },
                            actionText = "Ask a Question"
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(questions) { question ->
                                QuestionCard(
                                    question = question,
                                    onClick = {
                                        navController.navigate(
                                            NavigationRoutes.QuestionDetail.createRoute(question.id)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorDialog(
                        message = (questionsState as UiState.Error).message,
                        onDismiss = { viewModel.getAllQuestions() }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionCard(
    question: Question,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = question.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By ${question.author}",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(question.createdAt)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
} 