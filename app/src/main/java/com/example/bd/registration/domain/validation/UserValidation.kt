package com.example.bd.registration.domain.validation

import androidx.core.text.isDigitsOnly

object UserValidation {
    fun isNameValid(name: String): Boolean =
        name.isNotBlank()

    fun isAgeValid(age: String): Boolean =
        age.isNotBlank() && age.isDigitsOnly()

    fun isGenderValid(genders: List<String>, gender: String): Boolean =
        gender.isNotBlank() && genders.contains(gender)
}