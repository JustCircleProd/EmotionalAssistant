package com.example.bd.core.domain.repository

import com.example.bd.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insert(user: User)

    fun getUser(): Flow<User?>

    fun isUserTableNotEmpty(): Flow<Boolean>
}