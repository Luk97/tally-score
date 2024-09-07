package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.data.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.NONE
) {
    enum class DialogState {
        NONE,
        EDITING
    }


}