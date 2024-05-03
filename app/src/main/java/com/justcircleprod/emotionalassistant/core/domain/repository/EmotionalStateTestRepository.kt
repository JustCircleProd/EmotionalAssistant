package com.justcircleprod.emotionalassistant.core.domain.repository

import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTest
import kotlinx.coroutines.flow.Flow

interface EmotionalStateTestRepository {

    fun getAll(): Flow<List<EmotionalStateTest>>
}