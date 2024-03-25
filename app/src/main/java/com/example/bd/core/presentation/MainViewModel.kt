package com.example.bd.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(userRepository: UserRepository) : ViewModel() {

    val isUserTableNotEmpty = userRepository.isUserTableNotEmpty()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

    init {
        viewModelScope.launch {
            /*realm.write {
                val emotion = Emotion2().apply {
                    name = EmotionName.HAPPINESS
                    dateTime = LocalDateTime.now()
                }

                val emotionalStateTest = EmotionalStateTest2().apply {
                    name = EmotionalStateName.DEPRESSION
                    goalScore = 10
                }

                val question1 = EmotionalStateTestQuestion2().apply {
                    question = "question1"
                    points = 1
                }
                question1.options.addAll(listOf("1", "2", "3", "4", "5"))

                val question2 = EmotionalStateTestQuestion2().apply {
                    question = "question2"
                    points = 1
                }
                question2.options.addAll(listOf("1", "2", "3", "4", "5"))

                emotionalStateTest.questions.addAll(listOf(question1, question2))

                val emotionalStateTestResult = EmotionalStateTestResult2().apply {
                    score = 10
                    date = LocalDate.now()
                }
                emotionalStateTestResult.emotionalStateTest = emotionalStateTest

                val user = User2().apply {
                    name = "Вадим"
                    age = 23
                    gender = Gender.MALE
                }
                user.emotions.add(emotion)
                user.emotionalStateTestResults.add(emotionalStateTestResult)

                copyToRealm(emotion, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(emotionalStateTest, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(emotionalStateTestResult, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(user, updatePolicy = UpdatePolicy.ALL)
            }*/
        }
    }
}