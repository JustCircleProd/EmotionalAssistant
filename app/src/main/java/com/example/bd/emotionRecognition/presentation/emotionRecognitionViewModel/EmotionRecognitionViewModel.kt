package com.example.bd.emotionRecognition.presentation.emotionRecognitionViewModel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.domain.repository.InternalStorageRepository
import com.example.bd.emotionRecognition.utils.rotateImageIfNeeded
import com.example.bd.emotionRecognition.utils.toGrayscaleByteBuffer
import com.example.db.ml.Model
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EmotionRecognitionViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository,
    private val internalStorageRepository: InternalStorageRepository,
    private val emotionRecognitionModel: Model
) : ViewModel() {

    private val imageSize = 48
    private val faceDetector: FaceDetector = run {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()

        FaceDetection.getClient(options)
    }

    val recognizedEmotion = MutableStateFlow<EmotionName?>(null)

    val imageBitmap = MutableStateFlow<Bitmap?>(null)

    val recognitionStage = MutableStateFlow(EmotionRecognitionStage.FACE_DETECTION)

    private var imageFileName: String? = null

    val savedEmotionId = MutableStateFlow<ObjectId?>(null)

    override fun onCleared() {
        super.onCleared()
        emotionRecognitionModel.close()
    }

    fun onEvent(event: EmotionRecognitionEvent) {
        when (event) {
            EmotionRecognitionEvent.OnBackPressed -> {
                clearFields()
            }

            is EmotionRecognitionEvent.OnUriReady -> {
                preprocessImageBitmap(context = event.context, uri = event.uri)
            }

            EmotionRecognitionEvent.OnEmotionRecognitionByPhotoScreenLaunched -> {
                detectFace()
            }

            EmotionRecognitionEvent.OnEmotionResultConfirmed -> {
                saveEmotionResult()
            }
        }
    }

    private fun clearFields() {
        recognizedEmotion.value = null
        imageBitmap.value = null
        recognitionStage.value = EmotionRecognitionStage.FACE_DETECTION
        imageFileName = null
    }

    private fun preprocessImageBitmap(context: Context, uri: Uri) {
        imageBitmap.value = rotateImageIfNeeded(context, uri)
    }

    private fun detectFace() {
        val image = InputImage.fromBitmap(imageBitmap.value!!, 0)

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.Default) {
                        delay(1000)
                        cropDetectedFace(imageBitmap.value!!, faces[0])

                        imageFileName = internalStorageRepository.getImageFileName()
                        internalStorageRepository.saveImage(imageFileName!!, imageBitmap.value!!)

                        delay(2000)
                        classify()
                    }
                } else {
                    recognitionStage.value = EmotionRecognitionStage.FACE_NOT_DETECTED
                }
            }
            .addOnFailureListener {
                recognitionStage.value = EmotionRecognitionStage.ERROR
            }
            .addOnCanceledListener {
                recognitionStage.value = EmotionRecognitionStage.ERROR
            }
    }

    private fun cropDetectedFace(bitmap: Bitmap, face: Face) {
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

        imageBitmap.value = croppedBitmap
        recognitionStage.value = EmotionRecognitionStage.EMOTION_CLASSIFICATION
    }


    private fun classify() {
        try {
            val image = Bitmap.createScaledBitmap(imageBitmap.value!!, imageSize, imageSize, false)

            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(
                    intArrayOf(1, imageSize, imageSize, 1),
                    DataType.FLOAT32
                )
            val byteBuffer = image.toGrayscaleByteBuffer()

            inputFeature0.loadBuffer(byteBuffer)

            val outputs = emotionRecognitionModel.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray.toList()

            var max = 0f
            var maxIndex = 0
            confidences.forEachIndexed { index, element ->
                if (element > max) {
                    max = element
                    maxIndex = index
                }
            }

            recognizedEmotion.value = EmotionName.entries[maxIndex]
            recognitionStage.value = EmotionRecognitionStage.EMOTION_CLASSIFIED
        } catch (e: IOException) {
            recognitionStage.value = EmotionRecognitionStage.ERROR
        }
    }

    private fun saveEmotionResult() {
        viewModelScope.launch {
            val emotion = Emotion().apply {
                name = recognizedEmotion.value!!
                dateTime = LocalDateTime.now()
                imageFileName = this@EmotionRecognitionViewModel.imageFileName
            }

            emotionRepository.insert(emotion)
            savedEmotionId.value = emotion._id
        }
    }
}