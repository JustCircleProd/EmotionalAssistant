package com.example.bd.registration.presentation

import com.example.bd.core.domain.models.Gender

sealed class RegisterEvent {
    data class OnConfirmPressed(val name: String, val age: Int, val gender: Gender) :
        RegisterEvent()
}