package com.justcircleprod.emotionalassistant.di

import android.content.Context
import com.justcircleprod.emotionalassistant.core.data.initialData.InitialEmotionalStateTests
import com.justcircleprod.emotionalassistant.core.data.repository.EmotionRepositoryImpl
import com.justcircleprod.emotionalassistant.core.data.repository.EmotionalStateTestRepositoryImpl
import com.justcircleprod.emotionalassistant.core.data.repository.EmotionalStateTestResultRepositoryImpl
import com.justcircleprod.emotionalassistant.core.data.repository.InternalStorageRepositoryImpl
import com.justcircleprod.emotionalassistant.core.data.repository.UserRepositoryImpl
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTest
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import com.justcircleprod.emotionalassistant.core.domain.models.User
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestResultRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.InternalStorageRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.UserRepository
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
        EmotionalStateTestRepositoryImpl(realm)

    @Singleton
    @Provides
    fun provideEmotionalStateTestResultRepository(realm: Realm): EmotionalStateTestResultRepository =
        EmotionalStateTestResultRepositoryImpl(realm)

    @Singleton
    @Provides
    fun provideInternalStorageRepository(@ApplicationContext context: Context): InternalStorageRepository =
        InternalStorageRepositoryImpl(context)
}