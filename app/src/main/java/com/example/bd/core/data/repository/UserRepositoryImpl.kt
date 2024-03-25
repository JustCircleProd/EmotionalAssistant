package com.example.bd.core.data.repository

import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.UserRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.asFlow
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val realm: Realm) :
    UserRepository {
    override suspend fun insert(user: User) {
        withContext(Dispatchers.IO) {
            realm.write {
                copyToRealm(user, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    override fun isUserTableNotEmpty(): Flow<Boolean> =
        realm
            .query<User>()
            .find()
            .asFlow()
            .map {
                it.list.toList().isNotEmpty()
            }

    override fun getUser(): Flow<User?> =
        realm
            .query<User>()
            .find()
            .first()
            .asFlow()
            .map { it.obj }
}