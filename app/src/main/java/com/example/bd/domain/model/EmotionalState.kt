package com.example.bd.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotional_state")
data class EmotionalState(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String
)
