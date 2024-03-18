package com.example.bd.emotionRecognition.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface

fun rotateImageIfNeeded(context: Context, uri: Uri): Bitmap {
    val exifInterface = context.contentResolver.openInputStream(uri).use { stream ->
        ExifInterface(stream!!)
    }

    val bitmap = context.contentResolver.openInputStream(uri).use { stream ->
        BitmapFactory.decodeStream(stream)
    }

    val orientation = exifInterface.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_UNDEFINED
    )

    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90f)

        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180f)

        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270f)

        ExifInterface.ORIENTATION_NORMAL -> bitmap

        else -> bitmap
    }
}

private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}