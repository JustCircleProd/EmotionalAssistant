package com.justcircleprod.emotionalassistant.emotionRecognition.presentation.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionName
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.InternalStorageRepository
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.util.rotateImageIfNeeded
import com.justcircleprod.emotionalassistant.emotionRecognition.presentation.util.toGrayscaleByteBuffer
import com.justcircleprod.emotionalassistant.ml.EmotionRecognitionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class EmotionRecognitionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val emotionRepository: EmotionRepository,
    private val internalStorageRepository: InternalStorageRepository,
    private val emotionRecognitionModel: EmotionRecognitionModel,
    private val faceDetector: FaceDetector
) : ViewModel() {

    companion object {
        private const val IMAGE_SIZE = 48
    }

    val recognizedEmotion = MutableStateFlow<EmotionName?>(null)

    val imageBitmap = MutableStateFlow<Bitmap?>(null)

    val recognitionStage = MutableStateFlow(EmotionRecognitionStage.FACE_DETECTION)

    private var imageFileName: String? = null

    // Indicates a successful addition or update
    val savedEmotionId = MutableStateFlow<ObjectId?>(null)

    // dateTime is passed if the screen was opened to add emotion for certain date
    private val dateTime: LocalDateTime = run {
        if (!savedStateHandle.contains(NavigationItem.EmotionRecognitionMethodSelection.DATE_ARGUMENT_NAME)) {
            return@run LocalDateTime.now()
        }

        savedStateHandle.get<String>(NavigationItem.EmotionRecognitionMethodSelection.DATE_ARGUMENT_NAME)
            ?.let {
                LocalDate.parse(it).atTime(LocalTime.of(12, 0))
            } ?: LocalDateTime.now()
    }


    // emotionId is passed if the screen was opened to update the emotion
    val emotionIdToUpdate = run {
        if (!savedStateHandle.contains(NavigationItem.EmotionRecognitionMethodSelection.EMOTION_ID_TO_UPDATE_ARGUMENT_NAME)) return@run null

        savedStateHandle.get<String>(NavigationItem.EmotionRecognitionMethodSelection.EMOTION_ID_TO_UPDATE_ARGUMENT_NAME)
            ?.let {
                with(GsonBuilder().create()) {
                    fromJson(it, ObjectId::class.java)
                }
            }
    }

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

            EmotionRecognitionEvent.OnEmotionResultConfirmedToAdd -> {
                saveEmotion()
            }

            is EmotionRecognitionEvent.OnEmotionResultConfirmedToUpdate -> {
                updateEmotion(event.context)
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
                if (faces.isEmpty()) {
                    recognitionStage.value = EmotionRecognitionStage.FACE_NOT_DETECTED
                    return@addOnSuccessListener
                }

                viewModelScope.launch(Dispatchers.Default) {
                    delay(1000)
                    cropDetectedFace(imageBitmap.value!!, faces[0])

                    imageFileName = internalStorageRepository.getImageFileName()
                    internalStorageRepository.saveImage(imageFileName!!, imageBitmap.value!!)

                    delay(2000)
                    classifyEmotion()
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


    private fun classifyEmotion() {
        try {
            val image =
                Bitmap.createScaledBitmap(imageBitmap.value!!, IMAGE_SIZE, IMAGE_SIZE, false)

            val inputFeature0 =
                TensorBuffer.createFixedSize(
                    intArrayOf(1, IMAGE_SIZE, IMAGE_SIZE, 1),
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

    private fun saveEmotion() {
        viewModelScope.launch {
            val emotion = Emotion().apply {
                name = recognizedEmotion.value!!
                dateTime = this@EmotionRecognitionViewModel.dateTime
                imageFileName = this@EmotionRecognitionViewModel.imageFileName
            }

            emotionRepository.add(emotion)
            savedEmotionId.value = emotion.id
        }
    }

    private fun updateEmotion(context: Context) {
        if (emotionIdToUpdate == null) return

        viewModelScope.launch {
            val oldImageFileName =
                emotionRepository.getById(emotionIdToUpdate).first()?.imageFileName

            if (oldImageFileName != null) {
                internalStorageRepository.deleteImage(context, oldImageFileName)
            }

            emotionRepository.update(
                id = emotionIdToUpdate,
                newName = recognizedEmotion.value!!,
                newImageFileName = this@EmotionRecognitionViewModel.imageFileName
            )
            savedEmotionId.value = emotionIdToUpdate
        }
    }
}