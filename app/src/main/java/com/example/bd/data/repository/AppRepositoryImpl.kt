package com.example.bd.data.repository

import com.example.bd.data.database.AppDatabase
import com.example.bd.domain.model.EmotionalState
import com.example.bd.domain.model.EmotionalStateTest
import com.example.bd.domain.model.EmotionalStateTestQuestion
import com.example.bd.domain.model.EmotionalStateTestWithQuestions
import com.example.bd.domain.model.User
import com.example.bd.domain.repository.AppRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityScoped
class AppRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : AppRepository {

    override suspend fun insertUser(user: User) {
        appDatabase.userDao().insert(user)
    }

    override fun isUserTableNotEmpty(): Flow<Boolean> =
        appDatabase.userDao().isNotEmpty()

    override fun getUser(): Flow<User> =
        appDatabase.userDao().get()


    override fun getEmotionalStates(): Flow<List<EmotionalState>> =
        appDatabase.emotionalStateDao().getAll()

    override suspend fun insertEmotionalStates(emotionalStates: List<EmotionalState>) {
        for (emotionalState in emotionalStates) {
            appDatabase.emotionalStateDao().insert(emotionalState)
        }
    }

    override fun getTestsWithQuestions(): Flow<List<EmotionalStateTestWithQuestions>> =
        appDatabase.emotionalStateTestDao().getEmotionalStateTestsWithQuestions()

    override suspend fun insertEmotionalTestWithQuestions(
        emotionalStateTest: EmotionalStateTest,
        emotionalStateTestQuestions: List<EmotionalStateTestQuestion>
    ) {
        appDatabase.emotionalStateTestDao()
            .insertTestWithQuestions(emotionalStateTest, emotionalStateTestQuestions)
    }
}