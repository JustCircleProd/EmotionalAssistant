package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

suspend fun Context.createImageFile(): Uri {
    return withContext(Dispatchers.IO) {
        val imagePath = File(filesDir, "")
        val newFile = File(imagePath, "image.png")
        FileProvider.getUriForFile(this@createImageFile, "$packageName.fileprovider", newFile)
    }
}