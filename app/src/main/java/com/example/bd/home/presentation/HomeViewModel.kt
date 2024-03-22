package com.example.bd.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
) : ViewModel() {

    val user = userRepository.getUser()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null,
        )
}