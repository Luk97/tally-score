package com.nickel.tallyscore.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.utils.ContextProvider
import com.nickel.tallyscore.R
import com.nickel.tallyscore.core.snackbar.SnackBarController
import com.nickel.tallyscore.persistence.preferences.PreferenceManager
import com.nickel.tallyscore.player.Player
import com.nickel.tallyscore.player.PlayerRepository
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
internal class GameScreenViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                playerRepository.players.collectLatest { players ->
                    _state.update { it.copy(players = players) }
                }
            }
            launch {
                PreferenceManager.userPreferences.collectLatest { preferences ->
                    _state.update { it.copy(preferences = preferences) }
                }
            }
        }
    }


    fun onInteraction(interaction: GameInteraction) {
        when (interaction) {
            GameInteraction.AddPlayerClicked -> onAddPlayerClicked()
            is GameInteraction.AddPlayerConfirmed -> onAddPlayerConfirmed(
                interaction.name,
                interaction.score
            )

            is GameInteraction.EditPlayerClicked -> onEditPlayerClicked(interaction.player)
            is GameInteraction.EditPlayerConfirmed -> onEditPlayerConfirmed(
                interaction.player,
                interaction.name
            )

            is GameInteraction.DeletePlayerClicked -> onDeletePlayerClicked(interaction.player)

            is GameInteraction.AddScoreClicked -> onAddScoreClicked(interaction.player)
            is GameInteraction.AddScoreConfirmed -> onAddScoreConfirmed(
                interaction.player,
                interaction.score
            )

            is GameInteraction.EditScoreClicked -> onEditScoreClicked(
                interaction.player,
                interaction.index
            )

            is GameInteraction.EditScoreConfirmed -> onEditScoreConfirmed(
                interaction.player,
                interaction.index,
                interaction.score
            )

            is GameInteraction.DeleteScoreClicked -> onDeleteScoreClicked(
                interaction.player,
                interaction.index
            )

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
            playerRepository.deletePlayer(player)
            val context = ContextProvider.context
            SnackBarController.sendEvent(
                message = context.getString(R.string.player_deleted, player.name),
                actionLabel = context.getString(R.string.undo)
            ) {
                playerRepository.upsertPlayer(player)
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
            playerRepository.deletePlayerScore(player, index)
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddPlayerConfirmed(name: String, score: String) {
        viewModelScope.launch {
            playerRepository.upsertPlayer(Player(name = name, score.toIntList()))
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onEditPlayerConfirmed(player: Player, name: String) {
        viewModelScope.launch {
            playerRepository.upsertPlayer(player.copy(name = name))
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onAddScoreConfirmed(player: Player, score: String) {
        viewModelScope.launch {
            playerRepository.addPlayerScore(player, score.toSafeInt())
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }

    private fun onEditScoreConfirmed(player: Player, index: Int, score: String) {
        viewModelScope.launch {
            playerRepository.updatePlayerScore(player = player, index, score.toSafeInt())
        }
        _state.update { it.copy(dialogState = DialogState.None) }
    }
}