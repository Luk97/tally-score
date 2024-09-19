package com.nickel.tallyscore.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.core.snackbar.SnackBarController
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.datastore.PlayerRepository
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.utils.toIntList
import com.nickel.tallyscore.utils.toSafeInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.players.collectLatest { players ->
                _state.update { it.copy(players = players) }
            }
        }
    }

    fun onInteraction(interaction: GameInteraction) {
        when (interaction) {
            GameInteraction.AddPlayerClicked -> onAddPlayerClicked()
            is GameInteraction.AddPlayerConfirmed -> onAddPlayerConfirmed(interaction.name, interaction.score)
            is GameInteraction.EditPlayerClicked -> onEditPlayerClicked(interaction.player)
            is GameInteraction.EditPlayerConfirmed -> onEditPlayerConfirmed(interaction.player, interaction.name)
            is GameInteraction.DeletePlayerClicked -> onDeletePlayerClicked(interaction.player)

            is GameInteraction.AddScoreClicked -> onAddScoreClicked(interaction.player)
            is GameInteraction.AddScoreConfirmed -> onAddScoreConfirmed(interaction.player, interaction.score)

            is GameInteraction.EditScoreClicked -> onEditScoreClicked(interaction.player, interaction.index)
            is GameInteraction.EditScoreConfirmed -> onEditScoreConfirmed(interaction.player, interaction.index, interaction.score)
            is GameInteraction.DeleteScoreClicked -> onDeleteScoreClicked(interaction.player, interaction.index)

            GameInteraction.DialogDismissed -> onDialogDismissed()
        }
    }

    private fun onDialogDismissed() {
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddPlayerClicked() {
        _state.update { it.copy(dialogState = DialogState.AddingPlayer()) }
    }

    private fun onEditPlayerClicked(player: Player) {
        _state.update { it.copy(dialogState = DialogState.EditingPlayer(player)) }
    }

    private fun onDeletePlayerClicked(player: Player) {
        viewModelScope.launch {
            repository.deletePlayer(player)
            SnackBarController.sendEvent(
                message = "Player ${player.name} deleted",
                actionLabel = "Undo"
            ) {
                repository.upsertPlayer(player)
            }
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddScoreClicked(player: Player) {
        _state.update { it.copy(dialogState = DialogState.AddingScore(player)) }
    }

    private fun onEditScoreClicked(player: Player, index: Int) {
        _state.update {
            it.copy(
                dialogState = DialogState.EditingScore(
                    player = player,
                    index = index
                )
            )
        }
    }

    private fun onDeleteScoreClicked(player: Player, index: Int) {
        viewModelScope.launch {
            repository.deletePlayerScore(player, index)
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddPlayerConfirmed(name: String, score: String) {
        viewModelScope.launch {
            repository.upsertPlayer(Player(name = name, score.toIntList()))
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onEditPlayerConfirmed(player: Player, name: String) {
        viewModelScope.launch {
            repository.upsertPlayer(player.copy(name = name))
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddScoreConfirmed(player: Player, score: String) {
        viewModelScope.launch {
            repository.addPlayerScore(player, score.toSafeInt())
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onEditScoreConfirmed(player: Player, index: Int, score: String) {
        viewModelScope.launch {
            repository.updatePlayerScore(player = player, index, score.toSafeInt())
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }
}