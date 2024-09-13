package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.data.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.None
) {
    val playerCount: Int
        get() = players.size

    val turnCount: Int
        get() = players.maxOfOrNull{ it.turns } ?: 0

    val showTurns: Boolean
        get() = turnCount > 0

    val showTotals: Boolean
        get() = players.isNotEmpty()


    sealed class DialogState {

        data object None: DialogState()

        data class AddingPlayer(
            val name: String = "",
            val score: String = ""
        ): DialogState() {
            val validInput: Boolean
                get() = name.isNotEmpty()
        }

        data class AddingScore(
            val playerId: Long,
            val score: String = ""
        ): DialogState() {
            val validInput: Boolean
                get() = score.isNotEmpty()
        }

        data class EditingScore(
            val playerId: Long,
            val score: String,
            val index: Int
        ): DialogState() {
            val validInput: Boolean
                get() = score.isNotEmpty()
        }
    }
}