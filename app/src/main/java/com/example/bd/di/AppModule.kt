package com.example.bd.di

import android.content.Context
import com.example.bd.data.database.AppDatabase
import com.example.bd.data.repository.AppRepositoryImpl
import com.example.bd.domain.repository.AppRepository
import com.example.db.ml.Model
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
}