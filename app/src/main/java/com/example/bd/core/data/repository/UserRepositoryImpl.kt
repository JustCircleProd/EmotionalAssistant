package com.example.bd.core.data.repository

import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) :
    UserRepository {
    override suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().insert(user)
        }
    }

    override fun isUserTableNotEmpty(): Flow<Boolean> =
        appDatabase.userDao().isNotEmpty()

    override suspend fun getUser(): User =
        appDatabase.userDao().get()

    override fun getUserFlow(): Flow<User> =
        appDatabase.userDao().getFlow()
}