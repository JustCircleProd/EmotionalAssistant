package com.example.bd.core.presentation.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun formatLocalDate(localDate: LocalDate): String =
    localDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))


fun formatLocalTime(localTime: LocalTime): String =
    localTime.format(DateTimeFormatter.ofPattern("HH:mm"))