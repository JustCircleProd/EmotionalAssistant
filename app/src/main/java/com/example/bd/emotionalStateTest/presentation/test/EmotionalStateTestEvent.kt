package com.example.bd.emotionalStateTest.presentation.test

sealed class EmotionalStateTestEvent {
    data class OnGoToNextQuestionClick(val selectedOptionValue: Int) : EmotionalStateTestEvent()
    data class OnCompleteTestClick(val selectedOptionValue: Int) : EmotionalStateTestEvent()
}