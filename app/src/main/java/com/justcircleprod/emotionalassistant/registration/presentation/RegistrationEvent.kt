package com.justcircleprod.emotionalassistant.registration.presentation

sealed class RegistrationEvent {
    data class OnConfirmPressed(val name: String) : RegistrationEvent()
}