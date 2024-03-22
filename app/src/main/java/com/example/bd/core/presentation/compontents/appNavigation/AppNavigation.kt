package com.example.bd.core.presentation.compontents.appNavigation

enum class Screen {
    WELCOME,
    REGISTER,
    HOME,
    HISTORY,
    EMOTION_RECOGNITION_METHOD_SELECTION,
    EMOTION_RECOGNITION_BY_PHOTO,
    EMOTION_SELECTION_FROM_LIST,
    EMOTIONAL_STATE_TEST
}

sealed class NavigationItem(val route: String) {
    data object Welcome : NavigationItem(Screen.WELCOME.name)
    data object Register : NavigationItem(Screen.REGISTER.name)
    data object Home : NavigationItem(Screen.HOME.name)
    data object History : NavigationItem(Screen.HISTORY.name)
    data object EmotionRecognitionMethodSelection :
        NavigationItem(Screen.EMOTION_RECOGNITION_METHOD_SELECTION.name)

    data object EmotionRecognitionByPhoto : NavigationItem(Screen.EMOTION_RECOGNITION_BY_PHOTO.name)
    data object EmotionSelectionFromList : NavigationItem(Screen.EMOTION_SELECTION_FROM_LIST.name)
    data object EmotionalStateTest : NavigationItem(Screen.EMOTIONAL_STATE_TEST.name)
}