package com.example.bd.domain.repository

import com.example.bd.domain.model.EmotionalState
import com.example.bd.domain.model.EmotionalStateTest
import com.example.bd.domain.model.EmotionalStateTestQuestion
import com.example.bd.domain.model.EmotionalStateTestWithQuestions
import com.example.bd.domain.model.User
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