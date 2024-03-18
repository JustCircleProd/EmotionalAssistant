package com.example.bd.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.Emotion
import com.example.bd.core.domain.repository.EmotionRepository
import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val emotionRepository: EmotionRepository
) : ViewModel() {

    val isUserTableNotEmpty get() = userRepository.isUserTableNotEmpty()

    init {
        viewModelScope.launch {
            for (i in 1..7) {
                emotionRepository.insert(
                    Emotion(
                        id = i,
                        name = Emotion.EmotionName.entries[i - 1]
                    )
                )
            }
        }
    }
}