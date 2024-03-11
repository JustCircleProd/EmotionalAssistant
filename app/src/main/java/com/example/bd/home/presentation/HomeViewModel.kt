package com.example.bd.home.presentation

import androidx.lifecycle.ViewModel

import com.example.bd.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val user get() = userRepository.getUserFlow()
}