package com.example.bd.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "emotional_state_test_question",
    foreignKeys = [
        ForeignKey(
            entity = EmotionalStateTest::class,
            parentColumns = ["id"],
            childColumns = ["emotional_state_test_id"]
        )
    ]
)
data class EmotionalStateTestQuestion(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "options") val optionList: OptionList,
    @ColumnInfo("points") val pointList: PointList,
    @ColumnInfo("emotional_state_test_id", index = true) val emotionalStateTestId: Int
)

data class OptionList(
    val options: List<String>
)

data class PointList(
    val points: List<Int>
)
