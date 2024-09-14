package com.nickel.tallyscore.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.core.Validatable
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.datastore.PlayerRepository
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.utils.handlePotentialMissingComma
import com.nickel.tallyscore.utils.toIntList
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
            is GameInteraction.AddScoreClicked -> onAddScoreClicked(interaction.playerId)
            is GameInteraction.EditScoreClicked -> onEditScoreClicked(
                interaction.playerId,
                interaction.score,
                interaction.index
            )

            is GameInteraction.DeleteScoreClicked -> onDeleteScoreClicked(
                interaction.playerId,
                interaction.index
            )

            is GameInteraction.NameChanged -> onNameChanged(interaction.name)
            is GameInteraction.ScoreChanged -> onScoreChanged(interaction.score)
            GameInteraction.DialogConfirmed -> onDialogConfirmed()
        }
    }

    private fun onInfoClicked() {

    }

    private fun onResetClicked() {
        viewModelScope.launch {
            repository.resetAllPlayerScores()
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

    private fun onAddScoreClicked(playerId: Long) {
        _state.update { it.copy(dialogState = DialogState.AddingScore(playerId = playerId)) }
    }

    private fun onEditScoreClicked(playerId: Long, score: String, index: Int) {
        _state.update {
            it.copy(
                dialogState = DialogState.EditingScore(
                    playerId = playerId,
                    score = score,
                    index = index
                )
            )
        }
    }

    private fun onNameChanged(name: String) {
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

    private fun onScoreChanged(value: String) {
        val score = value.handlePotentialMissingComma()
        _state.update {
            it.copy(
                dialogState =
                when (it.dialogState) {
                    is DialogState.AddingPlayer -> DialogState.AddingPlayer(
                        name = it.dialogState.name,
                        score = score
                    )
                    is DialogState.AddingScore -> DialogState.AddingScore(
                        playerId = it.dialogState.playerId,
                        score = score
                    )
                    is DialogState.EditingScore -> DialogState.EditingScore(
                        playerId = it.dialogState.playerId,
                        score = score,
                        index = it.dialogState.index
                    )
                    else -> DialogState.None
                }
            )
        }
    }

    private fun onDeleteScoreClicked(playerId: Long, index: Int) {
        viewModelScope.launch {
            repository.deletePlayerScore(playerId, index)
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onDialogConfirmed() {
        viewModelScope.launch {
            val dialogState = _state.value.dialogState
            if (dialogState is Validatable && dialogState.isValid) {
                when (dialogState) {
                    is DialogState.AddingPlayer -> repository.upsertPlayer(
                        Player(name = dialogState.name, scores = dialogState.score.toIntList())
                    )
                    is DialogState.AddingScore ->
                        repository.addPlayerScore(dialogState.playerId, dialogState.score.toInt())
                    is DialogState.EditingScore -> repository.updatePlayerScore(
                        dialogState.playerId,
                        dialogState.score.toInt(),
                        dialogState.index
                    )
                    else -> {}
                }
            }
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }
}

