package com.nickel.tallyscore.snackbar

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null
) {
    data class SnackBarAction(
        val label: String,
        val action: suspend () -> Unit
    )
}