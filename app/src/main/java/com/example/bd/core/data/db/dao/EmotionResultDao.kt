package com.example.bd.core.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bd.core.domain.models.EmotionResult
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionResultDao {
    @Query("SELECT * FROM emotion_result")
    fun getAll(): Flow<List<EmotionResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emotionResult: EmotionResult)

    @Delete
    suspend fun delete(emotionResult: EmotionResult)
}