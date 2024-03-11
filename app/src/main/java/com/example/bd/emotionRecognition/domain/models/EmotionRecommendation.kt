package com.example.bd.emotionRecognition.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotion_recommendation",
    foreignKeys = [
        ForeignKey(
            entity = Emotion::class,
            parentColumns = ["id"],
            childColumns = ["emotion_id"]
        )
    ]
)
data class EmotionRecommendation(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "recommendation") val recommendation: String,
    @ColumnInfo(name = "emotion_id", index = true) val emotionId: Int
)
