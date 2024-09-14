package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.data.Player

sealed class GameInteraction {
    data object InfoClicked: GameInteraction()
    data object ResetClicked: GameInteraction()
    data object DeleteClicked: GameInteraction()
    data object AddPlayerClicked: GameInteraction()
    data class EditPlayerClicked(val player: Player): GameInteraction()
    data class DeletePlayerClicked(val player: Player): GameInteraction()
    data class AddScoreClicked(val playerId: Long): GameInteraction()
    data class EditScoreClicked(val playerId: Long, val score: String, val index: Int): GameInteraction()
    data class DeleteScoreClicked(val playerId: Long, val index: Int): GameInteraction()
    data object DialogDismissed: GameInteraction()
    data object DialogConfirmed: GameInteraction()
    data class NameChanged(val name: String): GameInteraction()
    data class ScoreChanged(val score: String): GameInteraction()
}