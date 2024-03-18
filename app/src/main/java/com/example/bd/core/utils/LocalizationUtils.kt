package com.example.bd.core.utils

import android.content.Context
import com.example.bd.core.domain.models.Emotion
import com.example.db.R

fun getEmotionNameString(context: Context, emotionName: Emotion.EmotionName): String {
    return when (emotionName) {
        Emotion.EmotionName.ANGER -> context.getString(R.string.anger)
        Emotion.EmotionName.DISGUST -> context.getString(R.string.disgust)
        Emotion.EmotionName.FEAR -> context.getString(R.string.fear)
        Emotion.EmotionName.HAPPINESS -> context.getString(R.string.happiness)
        Emotion.EmotionName.NEUTRAL -> context.getString(R.string.neutral)
        Emotion.EmotionName.SAD -> context.getString(R.string.sad)
        Emotion.EmotionName.SURPRISE -> context.getString(R.string.surprise)
    }
}

fun getMonthName(context: Context, monthValue: Int): String {
    return when (monthValue) {
        1 -> context.getString(R.string.january)
        2 -> context.getString(R.string.february)
        3 -> context.getString(R.string.march)
        4 -> context.getString(R.string.april)
        5 -> context.getString(R.string.may)
        6 -> context.getString(R.string.june)
        7 -> context.getString(R.string.july)
        8 -> context.getString(R.string.august)
        9 -> context.getString(R.string.september)
        10 -> context.getString(R.string.october)
        11 -> context.getString(R.string.november)
        12 -> context.getString(R.string.december)
        else -> ""
    }
}