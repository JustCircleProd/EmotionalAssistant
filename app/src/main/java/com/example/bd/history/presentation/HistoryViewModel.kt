package com.example.bd.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.repository.EmotionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository
) : ViewModel() {

    val emotions = emotionRepository.getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val selectedDate = MutableStateFlow<LocalDate?>(null)

    val emotionsForSelectedDate: StateFlow<List<Emotion>> = combine(
        emotions,
        selectedDate
    ) { emotions, date ->
        emotions.filter { emotion ->
            date == null || emotion.dateTime.toLocalDate() == date
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun deleteEmotionResult(emotion: Emotion) {
        viewModelScope.launch {
            emotionRepository.delete(emotion)
        }
    }
}