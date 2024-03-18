package com.example.bd.core.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.bd.core.domain.models.EmotionResultWithEmotion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionResultWithEmotionDao {

    @Transaction
    @Query("SELECT * FROM emotion_result, emotion WHERE emotion_result.emotion_id = emotion.id")
    fun getEmotionResultsWithEmotion(): Flow<List<EmotionResultWithEmotion>>
}