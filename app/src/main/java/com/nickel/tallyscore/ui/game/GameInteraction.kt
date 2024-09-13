package com.nickel.tallyscore.ui.game

sealed class GameInteraction {
    data object InfoClicked: GameInteraction()
    data object ResetClicked: GameInteraction()
    data object DeleteClicked: GameInteraction()

    data object AddPlayerClicked: GameInteraction()
    data class AddScoreClicked(val playerId: Long): GameInteraction()
    data object DialogDismissed: GameInteraction()
    data object DialogConfirmed: GameInteraction()

    data class NameChanged(val name: String): GameInteraction()
    data class ScoreChanged(val score: String): GameInteraction()

}