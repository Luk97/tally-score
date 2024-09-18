package com.nickel.tallyscore.utils

object InputValidator {
    fun isValidName(name: String): Boolean = name.isNotBlank()

    fun isValidScore(score: String): Boolean = score.toIntOrNull() != null
}