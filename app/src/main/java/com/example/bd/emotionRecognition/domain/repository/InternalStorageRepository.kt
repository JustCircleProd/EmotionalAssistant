package com.example.bd.emotionRecognition.domain.repository

import android.graphics.Bitmap
import android.net.Uri

interface InternalStorageRepository {
    fun getImageFileName(): String

    fun saveImage(imageFileName: String, uri: Uri): Bitmap?

    suspend fun loadImage(imageFileName: String): Bitmap?
}