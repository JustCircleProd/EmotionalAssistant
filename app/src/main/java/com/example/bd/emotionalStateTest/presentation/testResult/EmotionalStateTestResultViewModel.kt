package com.example.bd.emotionalStateTest.presentation.testResult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.repository.EmotionalStateTestResultRepository
import com.example.bd.core.presentation.compontents.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EmotionalStateTestResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val emotionalStateTestResultRepository: EmotionalStateTestResultRepository
) : ViewModel() {

    private val date = run {
        if (!savedStateHandle.contains(NavigationItem.EmotionalStateTestResult.DATE_ARGUMENT_NAME)) {
            return@run null
        }

        savedStateHandle.get<String>(NavigationItem.EmotionRecognitionMethodSelection.DATE_ARGUMENT_NAME)
            ?.let {
                LocalDate.parse(it)
            }
    }

    val testResults = run {
        if (date != null) {
            emotionalStateTestResultRepository.getByDate(date).stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null,
            )
        } else {
            MutableStateFlow(null)
        }
    }
}