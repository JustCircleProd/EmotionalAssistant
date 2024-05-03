package com.justcircleprod.emotionalassistant.emotionDetail.presentation

import java.time.LocalDateTime

sealed class EmotionDetailEvent {
    data class OnAdditionalInfoConfirmed(val dateTime: LocalDateTime, val note: String) :
        EmotionDetailEvent()
}