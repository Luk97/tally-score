package com.nickel.tallyscore.ui.game.topbar

import com.nickel.tallyscore.player.Player

internal data class GameTopBarState(
    val players: List<Player> = emptyList(),
    val showMenu: Boolean = false,
    val showSettings: Boolean = false
)
