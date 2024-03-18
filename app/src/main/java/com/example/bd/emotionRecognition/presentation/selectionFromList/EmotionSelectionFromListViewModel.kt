package com.example.bd.emotionRecognition.presentation.selectionFromList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionResult
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.domain.repository.EmotionResultRepository
import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EmotionSelectionFromListViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val emotionRepository: EmotionRepository,
    private val emotionResultRepository: EmotionResultRepository
) : ViewModel() {
    val emotions get() = emotionRepository.getAll()

    val emotion = MutableStateFlow<Emotion?>(null)

    fun onEvent(event: EmotionSelectionFromListEvent) {
        when (event) {
            EmotionSelectionFromListEvent.OnEmotionResultConfirmed -> {
                saveEmotionResult()
            }
        }
    }

    private fun saveEmotionResult() {
        viewModelScope.launch {
            emotionResultRepository.insert(
                EmotionResult(
                    dateTime = LocalDateTime.now(),
                    userId = userRepository.getUser().id,
                    emotionId = emotion.value!!.id
                )
            )
        }
    }
}