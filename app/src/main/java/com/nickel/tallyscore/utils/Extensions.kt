package com.nickel.tallyscore.utils

fun String.handlePotentialMissingComma() = this.replace(".", "-")

fun String.toIntList() = this.toIntOrNull()?.let{ listOf(it) } ?: emptyList()

fun String.toSafeInt() = this.toIntOrNull() ?: 0