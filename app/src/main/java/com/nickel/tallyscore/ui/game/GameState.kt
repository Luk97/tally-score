package com.nickel.tallyscore.ui.game

import com.nickel.tallyscore.core.Validatable
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.utils.PlacementHelper

data class GameState(
    val players: List<Player> = emptyList(),
    val dialogState: DialogState = DialogState.None
) {

    val turnCount: Int
        get() = players.maxOfOrNull{ it.turns } ?: 0

    val placements: List<Int>
        get() = PlacementHelper.calculatePlacementOrder(players)

    val showTurns: Boolean
        get() = turnCount > 0

    val showTotals: Boolean
        get() = players.isNotEmpty()

    val showPlacements: Boolean
        get() = players.isNotEmpty()

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