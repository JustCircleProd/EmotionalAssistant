package com.example.bd.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.repository.EmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository
) : ViewModel() {

    val emotions = emotionRepository.getAll()

    val selectedDate = MutableStateFlow<LocalDate?>(null)

    fun deleteEmotionResult(emotion: Emotion) {
        viewModelScope.launch {
            emotionRepository.delete(emotion)
        }
    }
}