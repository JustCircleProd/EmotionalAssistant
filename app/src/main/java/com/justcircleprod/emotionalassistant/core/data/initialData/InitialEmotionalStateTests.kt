package com.justcircleprod.emotionalassistant.core.data.initialData

import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateName
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTest

object InitialEmotionalStateTests {
    fun get() = listOf(
        EmotionalStateTest().apply {
            emotionalStateName = EmotionalStateName.DEPRESSION
            questionsResourceEntryName = "depression_questions"
            numberOfQuestions = 8
            goalScore = 11
        },
        EmotionalStateTest().apply {
            emotionalStateName = EmotionalStateName.NEUROSIS
            questionsResourceEntryName = "neurosis_questions"
            numberOfQuestions = 6
            goalScore = 11
        },
        EmotionalStateTest().apply {
            emotionalStateName = EmotionalStateName.SOCIAL_PHOBIA
            questionsResourceEntryName = "social_phobia_questions"
            numberOfQuestions = 2
            goalScore = 3
        },
        EmotionalStateTest().apply {
            emotionalStateName = EmotionalStateName.ASTHENIA
            questionsResourceEntryName = "asthenia_questions"
            numberOfQuestions = 4
            goalScore = 6
        },
        EmotionalStateTest().apply {
            emotionalStateName = EmotionalStateName.INSOMNIA
            questionsResourceEntryName = "insomnia_questions"
            numberOfQuestions = 3
            goalScore = 6
        }
    )
}