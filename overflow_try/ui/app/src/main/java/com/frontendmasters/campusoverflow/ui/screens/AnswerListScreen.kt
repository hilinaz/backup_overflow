package com.frontendmasters.campusoverflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.frontendmasters.campusoverflow.api.models.Answer
import com.frontendmasters.campusoverflow.api.utils.UiState
import com.frontendmasters.campusoverflow.ui.components.ErrorDialog
import com.frontendmasters.campusoverflow.ui.components.LoadingIndicator
import com.frontendmasters.campusoverflow.viewmodel.AnswerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerListScreen(
    navController: NavController,
    questionId: String,
    viewModel: AnswerViewModel = hiltViewModel()
) {
    val answersState by viewModel.answersState.collectAsState()

    LaunchedEffect(questionId) {
        viewModel.getAnswers(questionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Answers") },
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
            when (answersState) {
                is UiState.Loading -> LoadingIndicator()
                is UiState.Success -> {
                    val answers = (answersState as UiState.Success<List<Answer>>).data
                    if (answers.isEmpty()) {
                        Text(
                            text = "No answers yet. Be the first to answer!",
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(answers) { answer ->
                                AnswerCard(answer = answer)
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorDialog(
                        message = (answersState as UiState.Error).message,
                        onDismiss = { navController.navigateUp() }
                    )
                }
            }
        }
    }
}

@Composable
fun AnswerCard(answer: Answer) {
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
                text = answer.answerText,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Answered by: ${answer.userId}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
} 