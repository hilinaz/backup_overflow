package com.frontendmasters.campusoverflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontendmasters.campusoverflow.ui.components.*
import com.frontendmasters.campusoverflow.viewmodel.QuestionViewModel
import com.frontendmasters.campusoverflow.viewmodel.AnswerViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun QuestionDetailScreen(
    navController: NavController,
    questionId: String,
    questionViewModel: QuestionViewModel = viewModel(),
    answerViewModel: AnswerViewModel = viewModel()
) {
    val questionState by questionViewModel.questionState.collectAsState()
    val answersState by answerViewModel.answersState.collectAsState()
    var newAnswer by remember { mutableStateOf("") }
    
    LaunchedEffect(questionId) {
        questionViewModel.getQuestionById(questionId)
        answerViewModel.getAnswersForQuestion(questionId)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Question Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (questionState) {
                is UiState.Loading -> {
                    LoadingIndicator()
                }
                is UiState.Success -> {
                    val question = (questionState as UiState.Success<Question>).data
                    if (question != null) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                QuestionDetailCard(question = question)
                            }
                            
                            item {
                                Text(
                                    text = "Answers",
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            
                            when (answersState) {
                                is UiState.Loading -> {
                                    item {
                                        LoadingIndicator()
                                    }
                                }
                                is UiState.Success -> {
                                    val answers = (answersState as UiState.Success<List<Answer>>).data
                                    if (answers.isEmpty()) {
                                        item {
                                            EmptyStateView(
                                                message = "No answers yet",
                                                onAction = { /* Focus on answer input */ },
                                                actionText = "Be the first to answer"
                                            )
                                        }
                                    } else {
                                        items(answers) { answer ->
                                            AnswerCard(answer = answer)
                                        }
                                    }
                                }
                                is UiState.Error -> {
                                    item {
                                        ErrorDialog(
                                            message = (answersState as UiState.Error).message,
                                            onDismiss = { answerViewModel.getAnswersForQuestion(questionId) }
                                        )
                                    }
                                }
                            }
                            
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                        
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            OutlinedTextField(
                                value = newAnswer,
                                onValueChange = { newAnswer = it },
                                label = { Text("Your Answer") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Button(
                                onClick = {
                                    if (newAnswer.isNotBlank()) {
                                        answerViewModel.postAnswer(questionId, newAnswer)
                                        newAnswer = ""
                                    }
                                },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Post Answer")
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorDialog(
                        message = (questionState as UiState.Error).message,
                        onDismiss = { questionViewModel.getQuestionById(questionId) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionDetailCard(question: Question) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.headlineSmall
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = question.description,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By ${question.author}",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(question.createdAt)),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun AnswerCard(answer: Answer) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = answer.content,
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By ${answer.author}",
                    style = MaterialTheme.typography.bodySmall
                )
                
                Text(
                    text = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                        .format(Date(answer.createdAt)),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
} 