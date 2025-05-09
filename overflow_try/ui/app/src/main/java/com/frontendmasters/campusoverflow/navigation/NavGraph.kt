package com.frontendmasters.campusoverflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.frontendmasters.campusoverflow.*
import com.frontendmasters.campusoverflow.ui.dashboard.DashboardScreen
import com.frontendmasters.campusoverflow.model.UserRole
import com.frontendmasters.campusoverflow.model.DashboardStats
import com.frontendmasters.campusoverflow.auth.AuthState
import com.frontendmasters.campusoverflow.ui.screens.*

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Dashboard : Screen("dashboard/{role}") {
        fun createRoute(role: UserRole) = "dashboard/${role.name}"
    }
    object Questions : Screen("questions/{role}/{userName}") {
        fun createRoute(role: UserRole, userName: String) = "questions/${role.name}/$userName"
    }
    object AdminQuestions : Screen("admin/questions")
    object Answers : Screen("answers/{questionId}")
    object UserList : Screen("admin/users")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Login.route
    ) {
        composable(NavigationRoutes.Login.route) {
            LoginScreen(navController)
        }
        
        composable(NavigationRoutes.Register.route) {
            RegisterScreen(navController)
        }
        
        composable(NavigationRoutes.QuestionList.route) {
            QuestionListScreen(navController)
        }
        
        composable(NavigationRoutes.QuestionDetail.route) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            questionId?.let {
                QuestionDetailScreen(navController, it)
            }
        }
        
        composable(NavigationRoutes.CreateQuestion.route) {
            CreateQuestionScreen(navController)
        }
        
        composable(NavigationRoutes.UserProfile.route) {
            UserProfileScreen(navController)
        }
        
        composable(NavigationRoutes.AnswerList.route) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getString("questionId")
            questionId?.let {
                AnswerListScreen(navController, it)
            }
        }
    }
} 