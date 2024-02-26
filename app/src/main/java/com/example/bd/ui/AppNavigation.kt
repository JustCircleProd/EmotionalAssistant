package com.example.bd.ui

enum class Screen {
    WELCOME,
    REGISTER,
    HOME,
    EMOTION_RECOGNITION
}

sealed class NavigationItem(val route: String) {
    data object Welcome : NavigationItem(Screen.WELCOME.name)
    data object Register : NavigationItem(Screen.REGISTER.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object EmotionRecognition: NavigationItem(Screen.EMOTION_RECOGNITION.name)
}