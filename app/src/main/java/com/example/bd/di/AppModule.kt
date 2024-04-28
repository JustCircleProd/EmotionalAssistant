package com.example.bd.di

import android.content.Context
import com.example.bd.core.data.initialData.InitialEmotionalStateTests
import com.example.bd.core.data.repository.EmotionRepositoryImpl
import com.example.bd.core.data.repository.EmotionalStateRepositoryImpl
import com.example.bd.core.data.repository.EmotionalStateTestResultRepositoryImpl
import com.example.bd.core.data.repository.InternalStorageRepositoryImpl
import com.example.bd.core.data.repository.UserRepositoryImpl
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionalStateTest
import com.example.bd.core.domain.models.EmotionalStateTestResult
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.domain.repository.EmotionalStateTestRepository
import com.example.bd.core.domain.repository.EmotionalStateTestResultRepository
import com.example.bd.core.domain.repository.InternalStorageRepository
import com.example.bd.core.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                User::class,
                Emotion::class,
                EmotionalStateTestResult::class,
                EmotionalStateTest::class
            )
        )
            .initialData {
                for (test in InitialEmotionalStateTests.get()) {
                    copyToRealm(test, updatePolicy = UpdatePolicy.ALL)
                }
            }
            .build()

        return Realm.open(config)
    }

    @Singleton
    @Provides
    fun provideUserRepository(realm: Realm): UserRepository =
        UserRepositoryImpl(realm)

    @Singleton
    @Provides
    fun provideEmotionRepository(realm: Realm): EmotionRepository =
        EmotionRepositoryImpl(realm)

    @Singleton
    @Provides
    fun provideEmotionalStateTestRepository(realm: Realm): EmotionalStateTestRepository =
        EmotionalStateRepositoryImpl(realm)

    @Singleton
    @Provides
    fun provideEmotionalStateTestResultRepository(realm: Realm): EmotionalStateTestResultRepository =
        EmotionalStateTestResultRepositoryImpl(realm)

    @Singleton
    @Provides
    fun provideInternalStorageRepository(@ApplicationContext context: Context): InternalStorageRepository =
        InternalStorageRepositoryImpl(context)
}