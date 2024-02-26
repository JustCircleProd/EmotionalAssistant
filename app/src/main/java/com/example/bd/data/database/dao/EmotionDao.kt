package com.example.bd.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bd.domain.model.Emotion
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionDao {

    @Query("SELECT * FROM emotion")
    fun getAll(): Flow<List<Emotion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(emotion: Emotion)
}