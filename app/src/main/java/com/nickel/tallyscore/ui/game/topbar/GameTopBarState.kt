package com.nickel.tallyscore.ui.game.topbar

import com.nickel.tallyscore.data.Player

data class GameTopBarState(
    val players: List<Player> = emptyList(),
    val showMenu: Boolean = false
)
