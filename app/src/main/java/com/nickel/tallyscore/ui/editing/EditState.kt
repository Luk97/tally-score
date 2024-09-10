package com.nickel.tallyscore.ui.editing

data class EditState(
    val name: String = "",
    val score: String = "0"
) {
    val validPlayer: Boolean
        get() = name.isNotEmpty() && score.isNotEmpty()
}