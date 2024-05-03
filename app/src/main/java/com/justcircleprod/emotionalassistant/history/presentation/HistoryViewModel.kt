package com.justcircleprod.emotionalassistant.history.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val emotionRepository: EmotionRepository,
    private val emotionalStateTestResultRepository: EmotionalStateTestResultRepository
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

    @OptIn(ExperimentalCoroutinesApi::class)
    val emotionalStateTestResultsForSelectedDate = selectedDate.flatMapLatest {
        if (it != null) {
            emotionalStateTestResultRepository.getByDate(it)
        } else {
            MutableStateFlow(emptyList())
        }
    }

    fun deleteEmotion(emotion: Emotion) {
        viewModelScope.launch {
            emotionRepository.delete(emotion)
        }
    }

    fun deleteEmotionalStateTestResult(testResult: EmotionalStateTestResult) {
        viewModelScope.launch {
            emotionalStateTestResultRepository.delete(testResult)
        }
    }
}