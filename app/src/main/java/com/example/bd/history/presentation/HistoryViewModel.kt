package com.example.bd.history.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.EmotionResult
import com.example.bd.core.domain.repository.EmotionResultRepository
import com.example.bd.core.domain.repository.EmotionResultWithEmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    emotionResultWithEmotionRepository: EmotionResultWithEmotionRepository,
    private val emotionResultRepository: EmotionResultRepository,
) : ViewModel() {

    val emotionResultsWithEmotion =
        emotionResultWithEmotionRepository.getEmotionResultsWithEmotion()

    val selectedDate = MutableStateFlow<LocalDate?>(null)

    init {
        viewModelScope.launch {
            emotionResultsWithEmotion.collect {
                Log.d("Tag111", it.toString())
            }

            emotionResultRepository.getAll().collect {
                Log.d("Tag111", it.toString())
            }
        }
    }

    fun deleteEmotionResult(emotionResult: EmotionResult) {
        viewModelScope.launch {
            Log.d("Tag111", "deleteEmotionResult")
            emotionResultRepository.delete(emotionResult)
        }
    }
}