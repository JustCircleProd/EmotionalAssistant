package com.example.bd.emotionRecognition.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.bd.emotionRecognition.domain.repository.InternalStorageRepository
import com.example.bd.util.rotateImageIfNeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

class InternalStorageRepositoryImpl @Inject constructor(private val context: Context) :
    InternalStorageRepository {

    override fun getImageFileName(): String = "image-${LocalDate.now()}.jpg"

    override fun saveImage(imageFileName: String, uri: Uri): Bitmap? {
        return try {
            val bitmap = rotateImageIfNeeded(context, uri)

            context.openFileOutput(imageFileName, Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }

            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
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