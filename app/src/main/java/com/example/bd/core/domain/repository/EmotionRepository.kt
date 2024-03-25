package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionName
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

interface EmotionRepository {
    suspend fun insert(emotion: Emotion)

    fun getAll(): Flow<List<Emotion>>

    fun getById(id: ObjectId): Flow<Emotion?>

    suspend fun delete(emotion: Emotion)

    suspend fun update(
        id: ObjectId,
        newName: EmotionName? = null,
        newDateTime: LocalDateTime? = null,
        newImageFileName: String? = "",
        newNote: String? = ""
    )
}