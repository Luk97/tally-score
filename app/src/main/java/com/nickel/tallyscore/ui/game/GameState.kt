package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.data.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.None
) {

    val turnCount: Int
        get() = players.maxOfOrNull{ it.turns } ?: 0

    val gameBoardVisible: Boolean
        get() = players.isNotEmpty()

    val showTotals: Boolean
        get() = gameBoardVisible

    val showPlacements: Boolean
        get() = gameBoardVisible

    val columnItemCount: Int
        get() = listOf(showTotals, showPlacements).count { it } + turnCount + 2

    sealed class DialogState {
        data object None: DialogState()
        data class AddingPlayer(val name: String = "", val score: String = ""): DialogState()
        data class EditingPlayer(val player: Player): DialogState()
        data class AddingScore(val player: Player): DialogState()
        data class EditingScore(val player: Player, val index: Int): DialogState()
    }
}