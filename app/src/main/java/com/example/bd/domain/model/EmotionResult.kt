package com.example.bd.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotion_result",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"]
        ),
        ForeignKey(
            entity = Emotion::class,
            parentColumns = ["id"],
            childColumns = ["emotion_id"]
        )
    ]
)
data class EmotionResult(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "emotion_id", index = true) val emotionId: Int
)
