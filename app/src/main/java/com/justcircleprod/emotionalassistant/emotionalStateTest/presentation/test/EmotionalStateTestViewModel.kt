package com.justcircleprod.emotionalassistant.emotionalStateTest.presentation.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTest
import com.justcircleprod.emotionalassistant.core.domain.models.EmotionalStateTestResult
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestRepository
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionalStateTestResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EmotionalStateTestViewModel @Inject constructor(
    emotionalStateTestRepository: EmotionalStateTestRepository,
    private val emotionalStateTestResultRepository: EmotionalStateTestResultRepository
) : ViewModel() {

    private val tests = emotionalStateTestRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    val currentQuestionNumber = MutableStateFlow(1)
    val numberOfQuestions = MutableStateFlow(0)

    private var currentTestIndex = 0

    val currentTest = MutableStateFlow<EmotionalStateTest?>(null)
    var currentTestQuestionIndex = 0

    private var currentTestScore = 0

    private val testResults = mutableListOf<EmotionalStateTestResult>()
    val testResultsDate: LocalDate = LocalDate.now()

    val areTestResultsSaved = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            tests.collect {
                if (!it.isNullOrEmpty()) {
                    currentTest.value = it[currentTestIndex]
                    numberOfQuestions.value = it.sumOf { test ->
                        test.numberOfQuestions
                    }
                }
            }
        }
    }

    fun onEvent(event: EmotionalStateTestEvent) {
        when (event) {
            is EmotionalStateTestEvent.OnGoToNextQuestionClick -> {
                goToNextQuestion(event.selectedOptionValue)
            }

            is EmotionalStateTestEvent.OnCompleteTestClick -> {
                completeTest(event.selectedOptionValue)
            }
        }
    }

    private fun goToNextQuestion(selectedOptionValue: Int) {
        currentTestScore += selectedOptionValue

        if (currentTestQuestionIndex < currentTest.value!!.numberOfQuestions - 1) {
            currentTestQuestionIndex++
            currentQuestionNumber.value++
            return
        }

        addTestResultToListOfTestResults()

        currentTestIndex++
        currentQuestionNumber.value++

        if (currentTestIndex < tests.value!!.size) {
            currentTest.value = tests.value!![currentTestIndex]
        }

        currentTestScore = 0
        currentTestQuestionIndex = 0
    }

    private fun completeTest(selectedOptionValue: Int) {
        currentTestScore += selectedOptionValue

        addTestResultToListOfTestResults()

        viewModelScope.launch {
            testResults.forEach {
                if (it.score > it.emotionalStateTest!!.goalScore) {
                    emotionalStateTestResultRepository.insert(it)
                }
            }

            areTestResultsSaved.value = true
        }
    }

    private fun addTestResultToListOfTestResults() {
        testResults.add(
            EmotionalStateTestResult().apply {
                emotionalStateTest = currentTest.value!!
                date = testResultsDate
                score = currentTestScore
            }
        )
    }
}