package com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel

import android.content.Context
import android.net.Uri

sealed class EmotionRecognitionEvent {

    data object OnBackPressed : EmotionRecognitionEvent()
    data object OnEmotionRecognitionByPhotoScreenLaunched : EmotionRecognitionEvent()
    data class OnUriReady(val context: Context, val uri: Uri) : EmotionRecognitionEvent()
    data object OnEmotionResultConfirmed : EmotionRecognitionEvent()
}