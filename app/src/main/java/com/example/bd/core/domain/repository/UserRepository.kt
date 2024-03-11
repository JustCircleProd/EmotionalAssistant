package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)

    suspend fun getUser(): User

    fun getUserFlow(): Flow<User>

    fun isUserTableNotEmpty(): Flow<Boolean>
}