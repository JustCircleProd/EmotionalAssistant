package com.example.bd.core.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotional_state_recommendation",
    foreignKeys = [
        ForeignKey(
            entity = EmotionalState::class,
            parentColumns = ["id"],
            childColumns = ["emotional_state_id"]
        )
    ]
)
data class EmotionalStateRecommendation(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "recommendation") val recommendation: String,
    @ColumnInfo(name = "emotional_state_id", index = true) val emotionalStateId: Int
)
