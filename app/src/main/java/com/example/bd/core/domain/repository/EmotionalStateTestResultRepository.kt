package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.EmotionalStateTestResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EmotionalStateTestResultRepository {

    suspend fun insert(testResult: EmotionalStateTestResult)

    fun getByDate(date: LocalDate): Flow<List<EmotionalStateTestResult>>

    suspend fun delete(testResult: EmotionalStateTestResult)
}