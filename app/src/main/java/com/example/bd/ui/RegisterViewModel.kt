package com.example.bd.ui

import androidx.lifecycle.ViewModel
import com.example.bd.data.repository.AppRepositoryImpl
import com.example.bd.domain.model.User
import com.example.bd.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {
        suspend fun registerUser(user: User) {
            withContext(Dispatchers.IO) {
                appRepository.insertUser(user)
            }
        }

}