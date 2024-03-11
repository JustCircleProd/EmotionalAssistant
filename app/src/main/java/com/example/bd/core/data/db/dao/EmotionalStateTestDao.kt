package com.example.bd.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.bd.core.domain.models.EmotionalStateTest
import com.example.bd.core.domain.models.EmotionalStateTestQuestion
import com.example.bd.core.domain.models.EmotionalStateTestWithQuestions
import kotlinx.coroutines.flow.Flow

@Dao
interface EmotionalStateTestDao {

    @Transaction
    suspend fun insertTestWithQuestions(
        emotionalStateTest: EmotionalStateTest,
        emotionalStateTestQuestions: List<EmotionalStateTestQuestion>
    ) {
        insertTest(emotionalStateTest)
        insertQuestions(emotionalStateTestQuestions)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTest(emotionalStateTest: EmotionalStateTest)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuestions(emotionalStateTestQuestions: List<EmotionalStateTestQuestion>)

    @Transaction
    @Query("SELECT * FROM emotional_state_test")
    fun getEmotionalStateTestsWithQuestions(): Flow<List<EmotionalStateTestWithQuestions>>
}