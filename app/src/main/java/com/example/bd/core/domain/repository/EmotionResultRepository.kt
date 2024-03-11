package com.example.bd.core.domain.repository

import com.example.bd.emotionRecognition.domain.models.EmotionResult

interface EmotionResultRepository {
    suspend fun insert(emotionResult: EmotionResult)
}