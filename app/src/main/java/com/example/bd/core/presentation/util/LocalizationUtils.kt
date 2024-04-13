package com.example.bd.core.presentation.util

import android.content.Context
import com.example.bd.core.domain.models.EmotionName
import com.example.bd.core.domain.models.Gender
import com.example.db.R

fun getGenderString(context: Context, gender: Gender): String {
    return when (gender) {
        Gender.MALE -> context.getString(R.string.male_gender)
        Gender.FEMALE -> context.getString(R.string.female_gender)
    }
}

fun getEmotionNameString(context: Context, emotionName: EmotionName?): String {
    return when (emotionName) {
        EmotionName.ANGER -> context.getString(R.string.anger)
        EmotionName.DISGUST -> context.getString(R.string.disgust)
        EmotionName.FEAR -> context.getString(R.string.fear)
        EmotionName.HAPPINESS -> context.getString(R.string.happiness)
        EmotionName.NEUTRAL -> context.getString(R.string.neutral)
        EmotionName.SAD -> context.getString(R.string.sad)
        EmotionName.SURPRISE -> context.getString(R.string.surprise)
        else -> context.getString(R.string.neutral)
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