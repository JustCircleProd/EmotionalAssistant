package com.example.bd.core.data.repository

import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.domain.models.EmotionResultWithEmotion
import com.example.bd.core.domain.repository.EmotionResultWithEmotionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmotionResultWithEmotionRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) :
    EmotionResultWithEmotionRepository {

    override fun getEmotionResultsWithEmotion(): Flow<List<EmotionResultWithEmotion>> =
        appDatabase.emotionResultWithEmotionDao().getEmotionResultsWithEmotion()
}