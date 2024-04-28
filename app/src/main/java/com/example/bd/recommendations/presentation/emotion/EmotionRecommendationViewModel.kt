package com.example.bd.recommendations.presentation.emotion

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.presentation.compontents.NavigationItem
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.mongodb.kbson.ObjectId
import javax.inject.Inject

@HiltViewModel
class EmotionRecommendationViewModel @Inject constructor(
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
}