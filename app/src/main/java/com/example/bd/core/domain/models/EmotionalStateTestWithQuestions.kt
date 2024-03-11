package com.example.bd.core.domain.models

import androidx.room.Embedded
import androidx.room.Relation

data class EmotionalStateTestWithQuestions(
    @Embedded val emotionalStateTest: EmotionalStateTest,
    @Relation(
        parentColumn = "id",
        entityColumn = "emotional_state_test_id"
    )
    val questions: List<EmotionalStateTestQuestion>
)
