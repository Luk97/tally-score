package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.player.Player

internal sealed class GameInteraction {
    data object AddPlayerClicked: GameInteraction()
    data class AddPlayerConfirmed(val name: String, val score: String): GameInteraction()

    data class EditPlayerClicked(val player: Player): GameInteraction()
    data class EditPlayerConfirmed(val player: Player, val name: String): GameInteraction()
    data class DeletePlayerClicked(val player: Player): GameInteraction()

    data class AddScoreClicked(val player: Player): GameInteraction()
    data class AddScoreConfirmed(val player: Player, val score: String): GameInteraction()

    data class EditScoreClicked(val player: Player, val index: Int): GameInteraction()
    data class EditScoreConfirmed(val player: Player, val index: Int, val score: String): GameInteraction()
    data class DeleteScoreClicked(val player: Player, val index: Int): GameInteraction()

    data object DialogDismissed: GameInteraction()
}