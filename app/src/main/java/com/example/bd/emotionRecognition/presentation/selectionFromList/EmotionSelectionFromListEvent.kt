package com.example.bd.emotionRecognition.presentation.selectionFromList

sealed class EmotionSelectionFromListEvent {
    data object OnEmotionResultConfirmed : EmotionSelectionFromListEvent()
}