package com.justcircleprod.emotionalassistant.core.domain.repository

import android.content.Context
import android.graphics.Bitmap

interface InternalStorageRepository {
    fun getImageFileName(): String

    fun saveImage(imageFileName: String, bitmap: Bitmap): Boolean

    fun deleteImage(context: Context, filename: String): Boolean
}