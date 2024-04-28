package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.EmotionalStateTest
import kotlinx.coroutines.flow.Flow

interface EmotionalStateTestRepository {

    fun getAll(): Flow<List<EmotionalStateTest>>
}