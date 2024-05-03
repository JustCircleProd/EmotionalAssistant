package com.justcircleprod.emotionalassistant.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.justcircleprod.emotionalassistant.core.domain.models.User
import com.justcircleprod.emotionalassistant.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val isUserRegistered = MutableStateFlow(false)

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnConfirmPressed -> {
                registerUser(
                    userName = event.name
                )
            }
        }
    }

    private fun registerUser(userName: String) {
        viewModelScope.launch {
            userRepository.insert(
                User().apply {
                    name = userName
                }
            )

            isUserRegistered.value = true
        }
    }
}