package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.Emotion
import kotlinx.coroutines.flow.Flow

interface EmotionRepository {
    suspend fun insert(emotion: Emotion)

    fun getAll(): Flow<List<Emotion>>

    suspend fun getById(id: Int): Emotion
}