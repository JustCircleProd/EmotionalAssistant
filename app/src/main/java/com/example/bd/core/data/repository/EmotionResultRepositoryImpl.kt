package com.example.bd.core.data.repository

import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.domain.models.EmotionResult
import com.example.bd.core.domain.repository.EmotionResultRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmotionResultRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) :
    EmotionResultRepository {

    override fun getAll(): Flow<List<EmotionResult>> =
        appDatabase.emotionResultDao().getAll()

    override suspend fun insert(emotionResult: EmotionResult) {
        withContext(Dispatchers.IO) {
            appDatabase.emotionResultDao().insert(emotionResult)
        }
    }

    override suspend fun delete(emotionResult: EmotionResult) {
        withContext(Dispatchers.IO) {
            appDatabase.emotionResultDao().delete(emotionResult)
        }
    }
}