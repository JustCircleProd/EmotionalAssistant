package com.justcircleprod.emotionalassistant.core.domain.repository

import com.justcircleprod.emotionalassistant.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun add(user: User)

    fun isNotEmpty(): Flow<Boolean>

    fun getUser(): Flow<User?>
}