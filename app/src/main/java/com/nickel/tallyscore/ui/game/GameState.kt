package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.data.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.None
) {
    sealed class DialogState {

        data object None: DialogState()

        data class AddingPlayer(
            val name: String = "",
            val score: String = "0"
        ): DialogState() {
            val validInput: Boolean
                get() = name.isNotEmpty() && score.isNotEmpty()
        }
    }
}