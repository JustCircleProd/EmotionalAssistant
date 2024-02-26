package com.example.bd.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.db.ml.Model
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

@HiltViewModel
class EmotionRecognitionViewModel @Inject constructor(private val model: Model) : ViewModel() {
    private val imageSize = 48
    private val faceDetector: FaceDetector = run {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()

        FaceDetection.getClient(options)
    }

    val recognizedEmotion = MutableStateFlow<String?>(null)
    val croppedFace = MutableStateFlow<Bitmap?>(null)

    override fun onCleared() {
        super.onCleared()

        model.close()
    }

    fun classifyEmotion(context: Context, imageUri: Uri) {
        viewModelScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                context.contentResolver.openInputStream(imageUri).use { stream ->
                    BitmapFactory.decodeStream(stream)
                }
            }

            detectFace(bitmap)
        }
    }

    private fun detectFace(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    viewModelScope.launch {
                        val croppedBitmap = cropDetectedFace(bitmap, faces[0])
                        classify(croppedBitmap)
                    }
                }
            }
            .addOnFailureListener { e ->

            }
    }

    private fun cropDetectedFace(bitmap: Bitmap, face: Face): Bitmap {
        val rect = face.boundingBox

        val x = rect.left.coerceAtLeast(0)
        val y = rect.top.coerceAtLeast(0)
        val width = rect.width()
        val height = rect.height()

        val croppedBitmap = Bitmap.createBitmap(
            bitmap,
            x,
            y,
            if (x + width > bitmap.width) bitmap.width - x else width,
            if (y + height > bitmap.height) bitmap.height - y else height,
        )

        croppedFace.value = croppedBitmap

        return croppedBitmap
    }

    private fun classify(bitmap: Bitmap) {
        try {
            val image = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(
                    intArrayOf(1, imageSize, imageSize, 1),
                    DataType.FLOAT32
                )
            val byteBuffer = image.toGrayscaleByteBuffer()

            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray.toList()

            // find the index of the class with the biggest confidence.
            val temp = mutableMapOf<String, Float>()
            val classes = arrayOf("Angry", "Disgust", "Fear", "Happy", "Neutral", "Sad", "Surprise")
            for (i in 0..6) {
                temp[classes[i]] = confidences[i]
            }

            recognizedEmotion.value = temp.maxBy { it.value }.key

            Log.d("Tag111", temp.maxBy { it.value }.toString())
            //result!!.text = classes[maxPos]
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    private fun Bitmap.toGrayscaleByteBuffer(): ByteBuffer {
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
}