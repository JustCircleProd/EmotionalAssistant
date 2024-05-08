package com.justcircleprod.emotionalassistant.di

import android.content.Context
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.justcircleprod.emotionalassistant.ml.EmotionRecognitionModel
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
    fun provideEmotionRecognitionModel(@ApplicationContext context: Context): EmotionRecognitionModel =
        EmotionRecognitionModel.newInstance(context)

    @ViewModelScoped
    @Provides
    fun provideFaceDetector(): FaceDetector {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()

        return FaceDetection.getClient(options)
    }
}