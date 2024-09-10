package com.nickel.tallyscore.ui.editing

sealed class EditInteraction {
    data class NameChanged(val name: String): EditInteraction()
    data class ScoreChanged(val score: String): EditInteraction()
    data object AddPlayerClicked: EditInteraction()
}