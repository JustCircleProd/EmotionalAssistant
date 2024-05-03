package com.justcircleprod.emotionalassistant.core.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.justcircleprod.emotionalassistant.core.domain.repository.InternalStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    override suspend fun loadImage(imageFileName: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            val bytes =
                files?.first { it.canRead() && it.isFile && it.name == imageFileName }?.readBytes()
            bytes?.size?.let {
                BitmapFactory.decodeByteArray(bytes, 0, it)
            }
        }
    }
}