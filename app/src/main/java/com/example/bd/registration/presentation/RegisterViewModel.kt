package com.example.bd.registration.presentation

import androidx.lifecycle.ViewModel
import com.example.bd.core.domain.models.User
import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    suspend fun registerUser(user: User) {
        userRepository.insertUser(user)
    }

}