package com.example.bd.registration.domain.validation

import androidx.core.text.isDigitsOnly
import com.example.bd.core.domain.models.Gender

object UserValidation {
    fun isNameValid(name: String): Boolean =
        name.isNotBlank()

    fun isAgeValid(age: String): Boolean =
        age.isNotBlank() && age.isDigitsOnly()

    fun isGenderValid(gender: Gender?): Boolean =
        gender != null
}