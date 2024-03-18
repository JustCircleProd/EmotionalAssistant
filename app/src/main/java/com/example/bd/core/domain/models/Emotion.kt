package com.example.bd.core.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotion")
data class Emotion(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: EmotionName
) {
    enum class EmotionName {
        ANGER,
        DISGUST,
        FEAR,
        HAPPINESS,
        NEUTRAL,
        SAD,
        SURPRISE
    }
}