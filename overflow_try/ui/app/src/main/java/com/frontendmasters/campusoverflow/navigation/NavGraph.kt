package com.frontendmasters.campusoverflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.frontendmasters.campusoverflow.model.User
import com.frontendmasters.campusoverflow.model.UserRole
import com.frontendmasters.campusoverflow.ui.screens.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object QuestionDetail : Screen("question/{questionId}") {
        fun createRoute(questionId: Int) = "question/$questionId"
    }
    object AnswerList : Screen("answers/{questionId}") {
        fun createRoute(questionId: Int) = "answers/$questionId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onSignInSuccess = { user ->
                    when (user.role) {
                        UserRole.ADMIN -> navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                        UserRole.TEACHER -> navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                        UserRole.STUDENT -> navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { user ->
                    when (user.role) {
                        UserRole.ADMIN -> navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                        UserRole.TEACHER -> navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                        UserRole.STUDENT -> navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                },
                onLoginClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onQuestionClick = { questionId ->
                    navController.navigate(Screen.QuestionDetail.createRoute(questionId))
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.QuestionDetail.route) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull() ?: return@composable
            QuestionDetailScreen(
                questionId = questionId,
                onBackClick = {
                    navController.navigateUp()
                },
                onViewAnswersClick = { questionId ->
                    navController.navigate(Screen.AnswerList.createRoute(questionId))
                }
            )
        }

        composable(Screen.AnswerList.route) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")?.toIntOrNull() ?: return@composable
            AnswerListScreen(
                questionId = questionId,
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    }
} 