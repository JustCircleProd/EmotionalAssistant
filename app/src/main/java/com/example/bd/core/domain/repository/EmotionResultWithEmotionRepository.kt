package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.EmotionResultWithEmotion
import kotlinx.coroutines.flow.Flow

interface EmotionResultWithEmotionRepository {

    fun getEmotionResultsWithEmotion(): Flow<List<EmotionResultWithEmotion>>
}