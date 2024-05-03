package com.justcircleprod.emotionalassistant.core.domain.repository

import com.justcircleprod.emotionalassistant.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insert(user: User)

    fun getUser(): Flow<User?>

    fun isNotEmpty(): Flow<Boolean>
}