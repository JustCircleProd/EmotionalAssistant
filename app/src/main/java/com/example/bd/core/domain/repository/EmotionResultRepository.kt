package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.EmotionResult
import kotlinx.coroutines.flow.Flow

interface EmotionResultRepository {
    fun getAll(): Flow<List<EmotionResult>>
    suspend fun insert(emotionResult: EmotionResult)
    suspend fun delete(emotionResult: EmotionResult)
}