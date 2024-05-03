package com.justcircleprod.emotionalassistant.core.domain.repository

import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionName
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

interface EmotionRepository {
    suspend fun insert(emotion: Emotion)

    fun getAll(): Flow<List<Emotion>>

    fun getById(id: ObjectId): Flow<Emotion?>

    suspend fun delete(emotion: Emotion)

    /**
     * If default values for properties, they will not be changed.
     */
    suspend fun update(
        id: ObjectId,
        newName: EmotionName? = null,
        newDateTime: LocalDateTime? = null,
        newImageFileName: String? = "",
        newNote: String? = ""
    )
}