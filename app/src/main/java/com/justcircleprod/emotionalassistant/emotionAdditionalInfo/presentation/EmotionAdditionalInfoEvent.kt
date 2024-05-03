package com.justcircleprod.emotionalassistant.emotionAdditionalInfo.presentation

import java.time.LocalDateTime

sealed class EmotionAdditionalInfoEvent {
    data class SaveAdditionalInfo(val dateTime: LocalDateTime, val note: String) :
        EmotionAdditionalInfoEvent()
}