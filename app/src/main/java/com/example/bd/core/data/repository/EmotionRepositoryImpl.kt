package com.example.bd.core.data.repository

import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.emotionRecognition.domain.models.Emotion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) :
    EmotionRepository {
    override suspend fun insert(emotion: Emotion) {
        withContext(Dispatchers.IO) {
            appDatabase.emotionDao().insert(emotion)
        }
    }

    override fun getAll(): Flow<List<Emotion>> =
        appDatabase.emotionDao().getAll()

    override suspend fun getById(id: Int): Emotion =
        withContext(Dispatchers.IO) {
            appDatabase.emotionDao().getById(id)
        }
}