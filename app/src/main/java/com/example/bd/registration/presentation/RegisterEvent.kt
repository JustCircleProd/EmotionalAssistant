package com.example.bd.registration.presentation

sealed class RegisterEvent {
    data class OnConfirmPressed(val name: String) : RegisterEvent()
}