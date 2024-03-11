package com.example.bd.emotionRecognition.presentation

import android.net.Uri

sealed class EmotionRecognitionEvent {

    data object OnBackPressedOnEmotionRecognitionByPhotoScreen : EmotionRecognitionEvent()
    data object OnEmotionRecognitionByPhotoScreenLaunched : EmotionRecognitionEvent()
    data class OnUriReady(val uri: Uri) : EmotionRecognitionEvent()
    data object OnEmotionResultConfirmed : EmotionRecognitionEvent()
}