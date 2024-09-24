package com.nickel.tallyscore.ui.game.topbar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.core.snackbar.SnackBarController
import com.nickel.tallyscore.datastore.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameTopBarViewModel @Inject constructor(
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
            repository.resetAllPlayerScores()
            SnackBarController.sendEvent("All Player Scores reset", "Undo") {
               repository.updatePlayerList(players)
            }
        }
    }

    private fun onDeleteAllPlayersClicked() {
        _state.update { it.copy(showMenu = false) }
        viewModelScope.launch {
            val players = _state.value.players
            repository.deleteAllPlayers()
            SnackBarController.sendEvent("All Players Deleted", "Undo") {
                repository.updatePlayerList(players)
            }
        }
    }
}