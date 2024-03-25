package com.example.bd.core.data.repository

import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.EmotionRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime
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

    override fun getById(id: ObjectId): Flow<Emotion?> {
        return realm
            .query<Emotion>("_id == $0", id)
            .find()
            .first()
            .asFlow()
            .map { it.obj }
    }

    override suspend fun delete(emotion: Emotion) {
        withContext(Dispatchers.IO) {
            realm.write {
                findLatest(emotion)?.also { delete(it) }
            }
        }
    }

    override suspend fun update(
        id: ObjectId,
        newName: EmotionName?,
        newDateTime: LocalDateTime?,
        newImageFileName: String?,
        newNote: String?
    ) {
        withContext(Dispatchers.IO) {
            val emotion = realm
                .query<Emotion>("_id == $0", id)
                .find()
                .first()

            realm.write {
                findLatest(emotion)?.let {
                    if (newName != null) {
                        it.name = newName
                    }
                    if (newDateTime != null) {
                        it.dateTime = newDateTime
                    }
                    if (newImageFileName == null || newImageFileName.isNotBlank()) {
                        it.imageFileName = newImageFileName
                    }
                    if (newNote == null || newNote.isNotBlank()) {
                        it.note = newNote
                    }

                    copyToRealm(it, updatePolicy = UpdatePolicy.ALL)
                }
            }
        }
    }
}