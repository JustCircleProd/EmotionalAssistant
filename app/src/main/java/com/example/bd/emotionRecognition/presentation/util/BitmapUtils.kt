package com.example.bd.emotionRecognition.presentation.util

import android.graphics.Bitmap
import android.graphics.Color
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun Bitmap.toGrayscaleByteBuffer(): ByteBuffer {
    // Every float value needs 4 bytes of memory
    val mImgData: ByteBuffer = ByteBuffer.allocateDirect(4 * width * height)
    mImgData.order(ByteOrder.nativeOrder())

    val pixels = IntArray(width * height)
    this.getPixels(pixels, 0, width, 0, 0, width, height)

    for (pixel in pixels) {
        // After grayscale conversion, every channel shares the same color value
        // but I stay with the original conversion formula (in case grayscale conversion isn't there)
        val grayscaleValue =
            0.2989 * Color.red(pixel) + 0.5870 * Color.green(pixel) + 0.1140 * Color.blue(pixel)
        // Normalize color range

        mImgData.putFloat(grayscaleValue.toFloat() / 255f)
    }

    return mImgData
}