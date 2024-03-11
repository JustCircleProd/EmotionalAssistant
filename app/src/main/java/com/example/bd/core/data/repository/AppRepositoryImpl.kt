package com.example.bd.core.data.repository

import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.domain.models.EmotionalState
import com.example.bd.core.domain.models.EmotionalStateTest
import com.example.bd.core.domain.models.EmotionalStateTestQuestion
import com.example.bd.core.domain.models.EmotionalStateTestWithQuestions
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : AppRepository {

    override suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            appDatabase.userDao().insert(user)
        }
    }

    override fun isUserTableNotEmpty(): Flow<Boolean> =
        appDatabase.userDao().isNotEmpty()

    override fun getUser(): Flow<User> =
        appDatabase.userDao().getFlow()


    override fun getEmotionalStates(): Flow<List<EmotionalState>> =
        appDatabase.emotionalStateDao().getAll()

    override suspend fun insertEmotionalStates(emotionalStates: List<EmotionalState>) {
        withContext(Dispatchers.IO) {
            for (emotionalState in emotionalStates) {
                appDatabase.emotionalStateDao().insert(emotionalState)
            }
        }
    }

    override fun getTestsWithQuestions(): Flow<List<EmotionalStateTestWithQuestions>> =
        appDatabase.emotionalStateTestDao().getEmotionalStateTestsWithQuestions()

    override suspend fun insertEmotionalTestWithQuestions(
        emotionalStateTest: EmotionalStateTest,
        emotionalStateTestQuestions: List<EmotionalStateTestQuestion>
    ) {
        withContext(Dispatchers.IO) {
            appDatabase.emotionalStateTestDao()
                .insertTestWithQuestions(emotionalStateTest, emotionalStateTestQuestions)
        }
    }
}