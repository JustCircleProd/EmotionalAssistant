package com.example.bd.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bd.core.domain.models.EmotionalState
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionalStateDao {

    @Query("SELECT * FROM emotional_state")
    fun getAll(): Flow<List<EmotionalState>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(emotionalState: EmotionalState)
}

/*@Query("SELECT * FROM emotional_state " +
            "JOIN emotional_state_test " +
            "ON emotional_state.id = emotional_state_test.emotional_state_id")
    fun getEmotionalStateWithTest(): Flow<Map<EmotionalState, List<EmotionalStateTest>>>

    @Transaction
    suspend fun insertEmotionalStateWithTests(emotionalState: EmotionalState, emotionalStateTests: List<EmotionalStateTest>) {
        insert(emotionalState)
        insertEmotionalStateTests(emotionalStateTests)
    }



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmotionalStateTests(emotionalStateTests: List<EmotionalStateTest>)*/