package com.nickel.tallyscore.ui.topbar

sealed class GameTopBarInteraction {
    data object MenuClicked: GameTopBarInteraction()
    data object MenuDismissed: GameTopBarInteraction()
}