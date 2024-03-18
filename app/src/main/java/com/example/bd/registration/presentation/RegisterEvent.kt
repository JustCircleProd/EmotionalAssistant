package com.example.bd.registration.presentation

import com.example.bd.core.domain.models.User

sealed class RegisterEvent {
    data class OnConfirmPressed(val user: User) : RegisterEvent()
}