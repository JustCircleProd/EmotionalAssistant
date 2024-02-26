package com.example.bd.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.data.repository.AppRepositoryImpl
import com.example.bd.domain.model.EmotionalState
import com.example.bd.domain.model.EmotionalStateTest
import com.example.bd.domain.model.EmotionalStateTestQuestion
import com.example.bd.domain.model.OptionList
import com.example.bd.domain.model.PointList
import com.example.bd.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {
    val emotionalStates = appRepository.getEmotionalStates()

    val emotionalStateTestsWithQuestions = appRepository.getTestsWithQuestions()

    init {
        viewModelScope.launch {
            appRepository.insertEmotionalStates(
                listOf(
                    EmotionalState(
                        id = 1,
                        name = "Депрессия"
                    ),
                    EmotionalState(
                        id = 2,
                        name = "Невроз"
                    ),
                    EmotionalState(
                        id = 3,
                        name = "Социофобия"
                    ),
                    EmotionalState(
                        id = 4,
                        name = "Астения"
                    ),
                    EmotionalState(
                        id = 5,
                        name = "Бессоница"
                    ),
                )
            )

            appRepository.insertEmotionalTestWithQuestions(
                emotionalStateTest = EmotionalStateTest(
                    id = 1,
                    name = "Тест на депрессию",
                    goalScore = 6,
                    emotionalStateId = 1
                ),
                emotionalStateTestQuestions = listOf(
                    EmotionalStateTestQuestion(
                        id = 1,
                        question = "У вас депрессия?",
                        optionList = OptionList(listOf("Да", "Нет", "Не знаю")),
                        pointList = PointList(listOf(6, 0, 3)),
                        emotionalStateTestId = 1
                    ),
                    EmotionalStateTestQuestion(
                        id = 2,
                        question = "У вас точно депрессия?",
                        optionList = OptionList(listOf("Да", "Нет", "Не знаю")),
                        pointList = PointList(listOf(6, 0, 3)),
                        emotionalStateTestId = 1
                    )
                )
            )

            appRepository.insertEmotionalTestWithQuestions(
                emotionalStateTest = EmotionalStateTest(
                    id = 2,
                    name = "Тест на социофобию",
                    goalScore = 6,
                    emotionalStateId = 3
                ),
                emotionalStateTestQuestions = listOf(
                    EmotionalStateTestQuestion(
                        id = 3,
                        question = "Вы боитесь людей?",
                        optionList = OptionList(listOf("Да", "Нет", "Не знаю")),
                        pointList = PointList(listOf(6, 0, 3)),
                        emotionalStateTestId = 2
                    )
                )
            )

            /*appRepository.insertUser(
                User(
                    id = 1,
                    name = "Вадим",
                    age = 23,
                    gender = "Мужской"
                )
            )*/

            /*appRepository.insertEmotions(
                listOf(
                    Emotion(
                        id = 1,
                        name = "Злость"
                    ),
                    Emotion(
                        id = 2,
                        name = "Отвращение"
                    ),
                    Emotion(
                        id = 3,
                        name = "Страх"
                    ),
                    Emotion(
                        id = 4,
                        name = "Счастье"
                    ),
                    Emotion(
                        id = 5,
                        name = "Грусть"
                    ),
                    Emotion(
                        id = 6,
                        name = "Удивление"
                    ),
                    Emotion(
                        id = 7,
                        name = "Нейтральность"
                    ),
                )
            )*/
        }
    }
}