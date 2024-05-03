package com.justcircleprod.emotionalassistant.core.data.repository

import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTest
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EmotionalStateTestRepositoryImpl @Inject constructor(private val realm: Realm) :
    EmotionalStateTestRepository {

    override fun getAll(): Flow<List<EmotionalStateTest>> =
        realm
            .query<EmotionalStateTest>()
            .find()
            .asFlow()
            .map { it.list }
}