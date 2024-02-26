package com.example.bd.domain.model

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
