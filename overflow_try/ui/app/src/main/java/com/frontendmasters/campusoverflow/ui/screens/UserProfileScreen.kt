package com.frontendmasters.campusoverflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontendmasters.campusoverflow.navigation.NavigationRoutes
import com.frontendmasters.campusoverflow.ui.components.*
import com.frontendmasters.campusoverflow.viewmodel.UserProfileViewModel
import com.frontendmasters.campusoverflow.viewmodel.AuthViewModel

@Composable
fun UserProfileScreen(
    navController: NavController,
    userProfileViewModel: UserProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val userState by userProfileViewModel.userState.collectAsState()
    val userStatsState by userProfileViewModel.userStatsState.collectAsState()
    
    LaunchedEffect(Unit) {
        userProfileViewModel.getUserProfile()
        userProfileViewModel.getUserStats()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            authViewModel.logout()
                            navController.navigate(NavigationRoutes.Login.route) {
                                popUpTo(NavigationRoutes.UserProfile.route) { inclusive = true }
                            }
                        }
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
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
            when (userState) {
                is UiState.Loading -> {
                    LoadingIndicator()
                }
                is UiState.Success -> {
                    val user = (userState as UiState.Success<User>).data
                    if (user != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = user.fullName,
                                        style = MaterialTheme.typography.headlineMedium
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    Text(
                                        text = user.email,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            when (userStatsState) {
                                is UiState.Loading -> {
                                    LoadingIndicator()
                                }
                                is UiState.Success -> {
                                    val stats = (userStatsState as UiState.Success<UserStats>).data
                                    if (stats != null) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            StatCard(
                                                title = "Questions",
                                                value = stats.totalQuestions.toString()
                                            )
                                            StatCard(
                                                title = "Answers",
                                                value = stats.totalAnswers.toString()
                                            )
                                        }
                                    }
                                }
                                is UiState.Error -> {
                                    ErrorDialog(
                                        message = (userStatsState as UiState.Error).message,
                                        onDismiss = { userProfileViewModel.getUserStats() }
                                    )
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    ErrorDialog(
                        message = (userState as UiState.Error).message,
                        onDismiss = { userProfileViewModel.getUserProfile() }
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
} 