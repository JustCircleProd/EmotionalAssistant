package com.justcircleprod.emotionalassistant.registration.presentation

sealed class RegisterEvent {
    data class OnConfirmPressed(val name: String) : RegisterEvent()
}