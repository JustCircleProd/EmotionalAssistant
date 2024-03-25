package com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel

enum class EmotionRecognitionStage {
    FACE_DETECTION,
    FACE_NOT_DETECTED,
    EMOTION_CLASSIFICATION,
    EMOTION_CLASSIFIED,
    ERROR
}