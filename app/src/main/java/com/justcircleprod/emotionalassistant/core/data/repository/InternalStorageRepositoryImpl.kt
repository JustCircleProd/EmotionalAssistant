package com.justcircleprod.emotionalassistant.core.data.repository

import android.content.Context
import android.graphics.Bitmap
import com.justcircleprod.emotionalassistant.core.domain.repository.InternalStorageRepository
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class InternalStorageRepositoryImpl @Inject constructor(private val context: Context) :
    InternalStorageRepository {

    override fun getImageFileName(): String = "image-${LocalDateTime.now()}.jpg"

    override fun saveImage(imageFileName: String, bitmap: Bitmap): Boolean {
        return try {
            context.openFileOutput(imageFileName, Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    override fun deleteImage(
        context: Context,
        filename: String
    ): Boolean {
        return try {
            context.deleteFile(filename)
        } catch (e: Exception) {
            false
        }
    }
}