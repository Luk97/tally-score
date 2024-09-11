package com.nickel.tallyscore.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.datastore.PlayerRepository
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
            GameInteraction.InfoClicked -> onInfoClicked()
            GameInteraction.ResetClicked -> onResetClicked()
            GameInteraction.DeleteClicked -> onDeleteClicked()
            GameInteraction.DialogDismissed -> onDialogDismissed()
            GameInteraction.AddPlayerClicked -> onAddPlayerClicked()
            is GameInteraction.PlayerNameChanged -> onPlayerNameChanged(interaction.name)
            is GameInteraction.PlayerScoreChanged -> onPlayerScoreChanged(interaction.score)
            GameInteraction.PlayerAdded -> onPlayerAdded()
        }
    }

    private fun onInfoClicked() {

    }

    private fun onResetClicked() {
        viewModelScope.launch {
            repository.resetPlayerScores()
        }
    }

    private fun onDeleteClicked() {
        viewModelScope.launch {
            repository.deleteAllPlayers()
        }
    }

    private fun onDialogDismissed() {
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddPlayerClicked() {
        _state.update { it.copy(dialogState = DialogState.AddingPlayer()) }
    }

    private fun onPlayerNameChanged(name: String) {
        when (val dialogState = _state.value.dialogState) {
            is DialogState.AddingPlayer -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.AddingPlayer(
                            name = name,
                            score = dialogState.score
                        )
                    )
                }
            }
            else -> {}
        }

    }

    private fun onPlayerScoreChanged(value: String) {
        when (val dialogState = _state.value.dialogState) {
            is DialogState.AddingPlayer -> {
                val score = value
                    .filter { it.isDigit() }
                    .toIntOrNull()
                    ?.toString()
                    ?: value
                _state.update {
                    it.copy(
                        dialogState = DialogState.AddingPlayer(
                            name = dialogState.name,
                            score = score
                        )
                    )
                }
            }
            else -> {}
        }
    }

    private fun onPlayerAdded() {

        viewModelScope.launch {
            when (val dialogState = _state.value.dialogState) {
                is DialogState.AddingPlayer -> {
                    dialogState.score.toInt().let {
                        val player = Player(name = dialogState.name, score = it)
                        repository.upsertPlayer(player)
                    }
                }
                else -> {}
            }
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }
}

