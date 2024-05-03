package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel

enum class EmotionRecognitionStage {
    FACE_DETECTION,
    FACE_NOT_DETECTED,
    EMOTION_CLASSIFICATION,
    EMOTION_CLASSIFIED,
    ERROR
}