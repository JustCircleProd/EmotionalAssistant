package com.example.bd.core.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotional_state_test_result",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = EmotionalStateTest::class,
            parentColumns = ["id"],
            childColumns = ["emotional_state_test_id"]
        )
    ]
)
data class EmotionalStateTestResult(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "emotional_state_test_id", index = true) val emotionalStateTestId: Int
)
