package com.example.bd.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bd.core.domain.models.Emotion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {

    @Query("SELECT * FROM emotion")
    fun getAll(): Flow<List<Emotion>>

    @Query("SELECT * FROM emotion WHERE id = :id")
    suspend fun getById(id: Int): Emotion

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(emotion: Emotion)
}