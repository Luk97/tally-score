package com.nickel.tallyscore.ui.game

sealed class GameInteraction {
    data object InfoClicked: GameInteraction()
    data object ResetClicked: GameInteraction()
    data object DeleteClicked: GameInteraction()
    data object DialogDismissed: GameInteraction()
    data object AddPlayerClicked: GameInteraction()
    data class PlayerNameChanged(val name: String): GameInteraction()
    data class PlayerScoreChanged(val score: String): GameInteraction()
    data object PlayerAdded: GameInteraction()
}