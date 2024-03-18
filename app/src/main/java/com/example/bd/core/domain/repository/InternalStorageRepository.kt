package com.example.bd.core.domain.repository

import android.graphics.Bitmap

interface InternalStorageRepository {
    fun getImageFileName(): String

    fun saveImage(imageFileName: String, bitmap: Bitmap): Boolean

    suspend fun loadImage(imageFileName: String): Bitmap?
}