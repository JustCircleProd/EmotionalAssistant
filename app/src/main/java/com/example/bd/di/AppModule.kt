package com.example.bd.di

import android.content.Context
import com.example.bd.core.data.db.AppDatabase
import com.example.bd.core.data.repository.AppRepositoryImpl
import com.example.bd.core.data.repository.EmotionRepositoryImpl
import com.example.bd.core.data.repository.EmotionResultRepositoryImpl
import com.example.bd.core.data.repository.UserRepositoryImpl
import com.example.bd.core.domain.repository.AppRepository
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.domain.repository.EmotionResultRepository
import com.example.bd.core.domain.repository.UserRepository
import com.example.bd.emotionRecognition.data.repository.InternalStorageRepositoryImpl
import com.example.bd.emotionRecognition.domain.repository.InternalStorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideAppRepository(appDatabase: AppDatabase): AppRepository =
        AppRepositoryImpl(appDatabase)

    @Singleton
    @Provides
    fun provideUserRepository(appDatabase: AppDatabase): UserRepository =
        UserRepositoryImpl(appDatabase)

    @Singleton
    @Provides
    fun provideEmotionRepository(appDatabase: AppDatabase): EmotionRepository =
        EmotionRepositoryImpl(appDatabase)

    @Singleton
    @Provides
    fun provideEmotionResultRepository(appDatabase: AppDatabase): EmotionResultRepository =
        EmotionResultRepositoryImpl(appDatabase)

    @Singleton
    @Provides
    fun provideInternalStorageRepository(@ApplicationContext context: Context): InternalStorageRepository =
        InternalStorageRepositoryImpl(context)
}