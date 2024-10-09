package com.nickel.tallyscore.ui.game.topbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.R
import com.nickel.tallyscore.utils.ContextProvider
import com.nickel.tallyscore.snackbar.SnackBarController
import com.nickel.tallyscore.persistence.player.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GameTopBarViewModel @Inject constructor(
    private val repository: PlayerRepository
): ViewModel() {
    private val _state = MutableStateFlow(GameTopBarState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.players.collectLatest { players ->
                _state.update { it.copy(players = players) }
            }
        }
    }

    fun onInteraction(interaction: GameTopBarInteraction) {
        when (interaction) {
            GameTopBarInteraction.MenuClicked -> onMenuClicked()
            GameTopBarInteraction.MenuDismissed -> onMenuDismissed()
            GameTopBarInteraction.ResetPointsClicked -> onResetPointsClicked()
            GameTopBarInteraction.DeletePlayersClicked -> onDeleteAllPlayersClicked()
            GameTopBarInteraction.SettingsClicked -> onSettingsClicked()
            GameTopBarInteraction.SettingsDismissed -> onSettingsDismissed()
        }
    }

    private fun onMenuClicked() {
        _state.update { it.copy(showMenu = true) }
    }

    private fun onMenuDismissed() {
        _state.update { it.copy(showMenu = false) }
    }

    private fun onResetPointsClicked() {
        _state.update { it.copy(showMenu = false) }
        viewModelScope.launch {
            val players = _state.value.players
            val context = ContextProvider.context
            repository.resetAllPlayerScores()
            SnackBarController.sendEvent(
                message =  context.getString(R.string.all_scores_reset),
                actionLabel =  context.getString(R.string.undo)
            ) {
               repository.updatePlayerList(players)
            }
        }
    }

    private fun onDeleteAllPlayersClicked() {
        _state.update { it.copy(showMenu = false) }
        viewModelScope.launch {
            val players = _state.value.players
            val context = ContextProvider.context
            repository.deleteAllPlayers()
            SnackBarController.sendEvent(
                message = context.getString(R.string.all_players_deleted),
                actionLabel = context.getString(R.string.undo)
            ) {
                repository.updatePlayerList(players)
            }
        }
    }

    private fun onSettingsClicked() {
        _state.update { it.copy(showMenu = false, showSettings = true) }
    }

    private fun onSettingsDismissed() {
        _state.update { it.copy(showSettings = false) }
    }
}