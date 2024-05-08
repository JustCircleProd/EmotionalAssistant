package com.justcircleprod.emotionalassistant.core.domain.repository

import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EmotionalStateTestResultRepository {

    suspend fun add(testResult: EmotionalStateTestResult)

    fun getByDate(date: LocalDate): Flow<List<EmotionalStateTestResult>>

    suspend fun delete(testResult: EmotionalStateTestResult)
}