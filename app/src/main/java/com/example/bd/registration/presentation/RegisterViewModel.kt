package com.example.bd.registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnConfirmPressed -> {
                viewModelScope.launch {
                    registerUser(event.user)
                }
            }
        }
    }

    private suspend fun registerUser(user: User) {
        userRepository.insertUser(user)
    }

}