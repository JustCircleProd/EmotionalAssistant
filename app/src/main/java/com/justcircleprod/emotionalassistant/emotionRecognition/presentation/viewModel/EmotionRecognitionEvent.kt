package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel

import android.content.Context
import android.net.Uri

sealed class EmotionRecognitionEvent {

    data object OnBackPressed : EmotionRecognitionEvent()
    data object OnEmotionRecognitionByPhotoScreenLaunched : EmotionRecognitionEvent()
    data class OnUriReady(val context: Context, val uri: Uri) : EmotionRecognitionEvent()
    data object OnEmotionResultConfirmedToAdd : EmotionRecognitionEvent()
    data class OnEmotionResultConfirmedToUpdate(val context: Context) : EmotionRecognitionEvent()
}