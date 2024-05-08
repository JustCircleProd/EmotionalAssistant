package com.justcircleprod.emotionalassistant.history.presentation

import android.content.Context
import com.justcircleprod.emotionalassistant.core.domain.models.Emotion
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import java.time.LocalDate

sealed class HistoryEvent {

    data class OnSelectedDateChanged(val selectedDate: LocalDate?) : HistoryEvent()
    data class OnDeleteEmotionalStateTestResultClick(val testResult: EmotionalStateTestResult) :
        HistoryEvent()

    data class OnDeleteEmotionClick(val context: Context, val emotion: Emotion) : HistoryEvent()
}