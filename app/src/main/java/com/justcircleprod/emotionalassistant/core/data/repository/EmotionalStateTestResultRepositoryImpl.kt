package com.justcircleprod.emotionalassistant.core.data.repository

import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTest
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import com.justcircleprod.emotionalassistant.core.domain.models.User
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestResultRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class EmotionalStateTestResultRepositoryImpl @Inject constructor(private val realm: Realm) :
    EmotionalStateTestResultRepository {

    override suspend fun add(testResult: EmotionalStateTestResult) {
        withContext(Dispatchers.IO) {
            realm.write {
                val copiedTestResult = EmotionalStateTestResult().apply {
                    emotionalStateTest =
                        findLatest(testResult.emotionalStateTest as EmotionalStateTest)
                    date = testResult.date
                    score = testResult.score
                }

                val user = realm.query<User>().find().first()
                findLatest(user)?.emotionalStateTestResults?.add(copiedTestResult)

                findLatest(user)?.let {
                    copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
                }
            }
        }
    }

    override fun getByDate(date: LocalDate): Flow<List<EmotionalStateTestResult>> {
        return realm
            .query<EmotionalStateTestResult>("dateDescription == $0", date.toString())
            .find()
            .asFlow()
            .map { it.list }
    }

    override suspend fun delete(testResult: EmotionalStateTestResult) {
        withContext(Dispatchers.IO) {
            realm.write {
                findLatest(testResult)?.also { delete(it) }
            }
        }
    }
}