package com.example.bd.core.data.repository

import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.EmotionRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmotionRepositoryImpl @Inject constructor(private val realm: Realm) :
    EmotionRepository {
    override suspend fun insert(emotion: Emotion) {
        withContext(Dispatchers.IO) {
            realm.write {
                val user = realm.query<User>().find().first()
                findLatest(user)?.emotions?.add(emotion)
                findLatest(user)?.let {
                    copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
                }
            }
        }
    }

    override fun getAll(): Flow<List<Emotion>> =
        realm
            .query<Emotion>()
            .find()
            .asFlow()
            .map { it.list }


    override suspend fun delete(emotion: Emotion) {
        realm.write {
            findLatest(emotion)?.also { delete(it) }
        }
    }
}