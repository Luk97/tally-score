package com.nickel.tallyscore.ui.game.topbar

sealed class GameTopBarInteraction {
    data object MenuClicked: GameTopBarInteraction()
    data object MenuDismissed: GameTopBarInteraction()
    data object ResetPointsClicked: GameTopBarInteraction()
    data object DeletePlayersClicked: GameTopBarInteraction()
}