package com.example.bd.core.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotional_state_test",
    foreignKeys = [
        ForeignKey(
            entity = EmotionalState::class,
            parentColumns = ["id"],
            childColumns = ["emotional_state_id"]
        )
    ]
)
data class EmotionalStateTest(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "goal_score") val goalScore: Int,
    @ColumnInfo(name = "emotional_state_id", index = true) val emotionalStateId: Int
)

