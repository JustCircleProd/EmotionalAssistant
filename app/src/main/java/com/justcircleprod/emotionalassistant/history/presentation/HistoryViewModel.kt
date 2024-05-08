package com.justcircleprod.emotionalassistant.history.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestResultRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.InternalStorageRepository
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
    private val emotionalStateTestResultRepository: EmotionalStateTestResultRepository,
    private val internalStorageRepository: InternalStorageRepository
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

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.OnSelectedDateChanged -> {
                selectedDate.value = event.selectedDate
            }

            is HistoryEvent.OnDeleteEmotionalStateTestResultClick -> {
                deleteEmotionalStateTestResult(event.testResult)
            }

            is HistoryEvent.OnDeleteEmotionClick -> {
                deleteEmotion(event.context, event.emotion)
            }
        }
    }

    private fun deleteEmotionalStateTestResult(testResult: EmotionalStateTestResult) {
        viewModelScope.launch {
            emotionalStateTestResultRepository.delete(testResult)
        }
    }

    private fun deleteEmotion(context: Context, emotion: Emotion) {
        viewModelScope.launch {
            val imageFileName = emotion.imageFileName

            if (imageFileName != null) {
                internalStorageRepository.deleteImage(context, imageFileName)
            }

            emotionRepository.delete(emotion)
        }
    }
}