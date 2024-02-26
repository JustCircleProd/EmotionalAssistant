package com.example.bd.di

import android.content.Context
import com.example.db.ml.Model
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @ViewModelScoped
    @Provides
    fun provideEmotionRecognitionModel(@ApplicationContext context: Context): Model =
        Model.newInstance(context)
}