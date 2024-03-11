package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.EmotionalState
import com.example.bd.core.domain.models.EmotionalStateTest
import com.example.bd.core.domain.models.EmotionalStateTestQuestion
import com.example.bd.core.domain.models.EmotionalStateTestWithQuestions
import com.example.bd.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    suspend fun insertUser(user: User)

    fun getUser(): Flow<User>

    fun isUserTableNotEmpty(): Flow<Boolean>

    fun getEmotionalStates(): Flow<List<EmotionalState>>

    suspend fun insertEmotionalStates(emotionalStates: List<EmotionalState>)

    fun getTestsWithQuestions(): Flow<List<EmotionalStateTestWithQuestions>>

    suspend fun insertEmotionalTestWithQuestions(
        emotionalStateTest: EmotionalStateTest,
        emotionalStateTestQuestions: List<EmotionalStateTestQuestion>
    )
}