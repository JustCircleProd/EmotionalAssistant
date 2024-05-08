package com.justcircleprod.emotionalassistant.core.presentation.util

import android.content.Context
import com.justcircleprod.emotionalassistant.R
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionName
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateName

fun getEmotionNameString(context: Context, emotionName: EmotionName?): String {
    return when (emotionName) {
        EmotionName.ANGER -> context.getString(R.string.anger)
        EmotionName.DISGUST -> context.getString(R.string.disgust)
        EmotionName.FEAR -> context.getString(R.string.fear)
        EmotionName.HAPPINESS -> context.getString(R.string.happiness)
        EmotionName.NEUTRAL -> context.getString(R.string.neutral)
        EmotionName.SADNESS -> context.getString(R.string.sadness)
        EmotionName.SURPRISE -> context.getString(R.string.surprise)
        else -> context.getString(R.string.neutral)
    }
}

fun getEmotionalStateNameString(context: Context, emotionalStateName: EmotionalStateName?): String {
    return when (emotionalStateName) {
        EmotionalStateName.DEPRESSION -> context.getString(R.string.depression)
        EmotionalStateName.NEUROSIS -> context.getString(R.string.neurosis)
        EmotionalStateName.SOCIAL_PHOBIA -> context.getString(R.string.social_phobia)
        EmotionalStateName.ASTHENIA -> context.getString(R.string.asthenia)
        EmotionalStateName.INSOMNIA -> context.getString(R.string.insomnia)
        else -> context.getString(R.string.depression)
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