package com.example.bd.ui

import androidx.lifecycle.ViewModel
import com.example.bd.data.repository.AppRepositoryImpl
import com.example.bd.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    val user get() = appRepository.getUser()
}