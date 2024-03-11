package com.example.bd.emotionRecognition.presentation

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.domain.repository.EmotionResultRepository
import com.example.bd.core.domain.repository.UserRepository
import com.example.bd.emotionRecognition.domain.models.Emotion
import com.example.bd.emotionRecognition.domain.models.EmotionResult
import com.example.bd.emotionRecognition.domain.repository.InternalStorageRepository
import com.example.bd.util.toGrayscaleByteBuffer
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
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

enum class EmotionRecognitionStage {
    FACE_DETECTION,
    EMOTION_CLASSIFICATION,
    EMOTION_CLASSIFIED
}

@HiltViewModel
class EmotionRecognitionViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val emotionRepository: EmotionRepository,
    private val emotionResultRepository: EmotionResultRepository,
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

    val emotion = MutableStateFlow<Emotion?>(null)
    val imageBitmap = MutableStateFlow<Bitmap?>(null)

    val recognitionStage = MutableStateFlow(EmotionRecognitionStage.FACE_DETECTION)

    private var imageFileName: String = ""

    override fun onCleared() {
        super.onCleared()
        emotionRecognitionModel.close()
    }

    fun onEvent(event: EmotionRecognitionEvent) {
        when (event) {
            EmotionRecognitionEvent.OnBackPressedOnEmotionRecognitionByPhotoScreen -> {
                clearFields()
            }

            is EmotionRecognitionEvent.OnUriReady -> {
                processImageBitmap(uri = event.uri)
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
        emotion.value = null
        imageBitmap.value = null
        recognitionStage.value = EmotionRecognitionStage.FACE_DETECTION
        imageFileName = ""
    }

    private fun processImageBitmap(uri: Uri) {
        imageFileName = internalStorageRepository.getImageFileName()
        val bitmap = internalStorageRepository.saveImage(imageFileName, uri)

        if (bitmap != null) {
            imageBitmap.value = bitmap
        }
    }

    private fun detectFace() {
        val image = InputImage.fromBitmap(imageBitmap.value!!, 0)

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.Default) {
                        cropDetectedFace(imageBitmap.value!!, faces[0])
                        classify()
                    }
                }
            }
            .addOnFailureListener { e ->

            }
    }

    private suspend fun cropDetectedFace(bitmap: Bitmap, face: Face) {
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

        delay(1000)

        imageBitmap.value = croppedBitmap
        recognitionStage.value = EmotionRecognitionStage.EMOTION_CLASSIFICATION
    }


    private suspend fun classify() {
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

            delay(2000)

            var max = 0f
            var maxIndex = 0
            confidences.forEachIndexed { index, element ->
                if (element > max) {
                    max = element
                    maxIndex = index
                }
            }

            emotion.value = emotionRepository.getById(maxIndex + 1)
            recognitionStage.value = EmotionRecognitionStage.EMOTION_CLASSIFIED
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    private fun saveEmotionResult() {
        viewModelScope.launch {
            emotionResultRepository.insert(
                EmotionResult(
                    date = LocalDate.now(),
                    userId = userRepository.getUser().id,
                    emotionId = emotion.value!!.id,
                    imageFileName = imageFileName
                )
            )
        }
    }
}