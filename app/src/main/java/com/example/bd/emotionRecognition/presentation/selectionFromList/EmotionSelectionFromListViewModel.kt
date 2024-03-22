package com.example.bd.emotionRecognition.presentation.selectionFromList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.domain.repository.EmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EmotionSelectionFromListViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository,
) : ViewModel() {
    val emotion = MutableStateFlow<EmotionName?>(null)

    fun onEvent(event: EmotionSelectionFromListEvent) {
        when (event) {
            EmotionSelectionFromListEvent.OnEmotionResultConfirmed -> {
                saveEmotionResult()
            }
        }
    }

    private fun saveEmotionResult() {
        viewModelScope.launch {
            emotionRepository.insert(
                Emotion().apply {
                    dateTime = LocalDateTime.now()
                    name = emotion.value!!
                }
            )
        }
    }
}