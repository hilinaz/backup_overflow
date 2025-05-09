package com.frontendmasters.campusoverflow.navigation

sealed class NavigationRoutes(val route: String) {
    object Login : NavigationRoutes("login")
    object Register : NavigationRoutes("register")
    object QuestionList : NavigationRoutes("questions")
    object QuestionDetail : NavigationRoutes("question/{questionId}") {
        fun createRoute(questionId: String) = "question/$questionId"
    }
    object CreateQuestion : NavigationRoutes("create_question")
    object UserProfile : NavigationRoutes("profile")
    object AnswerList : NavigationRoutes("answers/{questionId}") {
        fun createRoute(questionId: String) = "answers/$questionId"
    }
} 