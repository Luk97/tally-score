package com.nickel.tallyscore.ui.game

sealed class GameInteraction {
    data object InfoClicked: GameInteraction()
    data object SettingsClicked: GameInteraction()
    data object DialogDismissed: GameInteraction()
    data object AddPlayerClicked: GameInteraction()
}