package com.example.bd.core.domain.models

import androidx.room.Embedded
import androidx.room.Relation

data class EmotionResultWithEmotion(
    @Embedded
    val emotionResult: EmotionResult,
    @Relation(
        parentColumn = "emotion_id",
        entityColumn = "id"
    )
    val emotion: Emotion
)
