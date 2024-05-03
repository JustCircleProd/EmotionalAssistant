package com.justcircleprod.emotionalassistant.emotionAdditionalInfo.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justcircleprod.emotionalassistant.core.domain.repository.EmotionRepository
import com.justcircleprod.emotionalassistant.core.presentation.compontents.NavigationItem
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EmotionAdditionalInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val emotionRepository: EmotionRepository
) : ViewModel() {

    private val emotionId = run {
        if (!savedStateHandle.contains(NavigationItem.EmotionAdditionalInfo.EMOTION_ID_ARGUMENT_NAME)) return@run null

        savedStateHandle.get<String>(NavigationItem.EmotionAdditionalInfo.EMOTION_ID_ARGUMENT_NAME)
            ?.let {
                with(GsonBuilder().create()) {
                    fromJson(it, ObjectId::class.java)
                }
            }
    }

    val emotion = run {
        if (emotionId != null) {
            emotionRepository.getById(emotionId).stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                null,
            )
        } else {
            MutableStateFlow(null)
        }
    }

    fun onEvent(event: EmotionAdditionalInfoEvent) {
        when (event) {
            is EmotionAdditionalInfoEvent.SaveAdditionalInfo -> {
                updateEmotionDateTimeAndNote(event.dateTime, event.note)
            }
        }
    }

    private fun updateEmotionDateTimeAndNote(newDateTime: LocalDateTime, newNote: String) {
        emotionId ?: return

        viewModelScope.launch {
            emotionRepository.update(
                id = emotionId,
                newDateTime = newDateTime,
                newNote = newNote
            )
        }
    }
}
