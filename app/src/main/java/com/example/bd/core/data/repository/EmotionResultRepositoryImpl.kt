package com.example.bd.core.data.repository

import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.domain.repository.EmotionResultRepository
import com.example.bd.emotionRecognition.domain.models.EmotionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmotionResultRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) :
    EmotionResultRepository {

    override suspend fun insert(emotionResult: EmotionResult) {
        withContext(Dispatchers.IO) {
            appDatabase.emotionResultDao().insert(emotionResult)
        }
    }
}