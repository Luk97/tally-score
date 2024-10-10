package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.persistence.player.Player
import com.nickel.tallyscore.persistence.preferences.UserPreferences

internal data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.None,
    val preferences: UserPreferences = UserPreferences()
) {
    val turnCount: Int
        get() = players.maxOfOrNull{ it.turns } ?: 0

    val gameBoardVisible: Boolean
        get() = players.isNotEmpty()

    val showTotals: Boolean
        get() = gameBoardVisible

    val showPlacements: Boolean
        get() = gameBoardVisible

    val horizontalItemCount: Int
        get() = players.size + 1

    val verticalItemCount: Int
        get() = listOf(showTotals, showPlacements).count { it } + turnCount + 2

    sealed class DialogState {
        data object None: DialogState()
        data class AddingPlayer(val name: String = "", val score: String = ""): DialogState()
        data class EditingPlayer(val player: Player): DialogState()
        data class AddingScore(val player: Player): DialogState()
        data class EditingScore(val player: Player, val index: Int): DialogState()
    }
}