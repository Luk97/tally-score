package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.core.Validatable
import com.nickel.tallyscore.data.Player

data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.None
) {

    val turnCount: Int
        get() = players.maxOfOrNull{ it.turns } ?: 0

    val showLabels: Boolean
        get() = players.isNotEmpty()

    val showTotals: Boolean
        get() = showLabels

    val showPlacements: Boolean
        get() = showLabels

    val columnItemCount: Int
        get() = listOf(showTotals, showPlacements).count { it } + turnCount + 2

    sealed class DialogState {

        data object None: DialogState()

        data class AddingPlayer(
            val name: String = "",
            val score: String = ""
        ): DialogState(), Validatable {
            override val isValid: Boolean
                get() = name.isNotEmpty()
        }

        data class EditingPlayer(
            val player: Player
        ): DialogState(), Validatable {
            override val isValid: Boolean
                get() = player.name.isNotEmpty()
        }

        data class AddingScore(
            val playerId: Long,
            val score: String = ""
        ): DialogState(), Validatable {
            override val isValid: Boolean
                get() = score.toIntOrNull() != null
        }

        data class EditingScore(
            val playerId: Long,
            val score: String,
            val index: Int
        ): DialogState(), Validatable {
            override val isValid: Boolean
                get() = score.toIntOrNull() != null
        }
    }
}