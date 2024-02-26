package com.example.bd.ui

import androidx.lifecycle.ViewModel
import com.example.bd.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel1 @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    val isUserTableNotEmpty get() = appRepository.isUserTableNotEmpty()
}