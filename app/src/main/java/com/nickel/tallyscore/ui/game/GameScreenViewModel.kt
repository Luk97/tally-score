package com.nickel.tallyscore.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {

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
            GameInteraction.SettingsClicked -> onSettingsClicked()
            GameInteraction.DialogDismissed -> onDialogDismissed()
            GameInteraction.AddPlayerClicked -> onAddPlayerClicked()
        }
    }

    private fun onInfoClicked() {

    }

    private fun onSettingsClicked() {

    }

    private fun onDialogDismissed() {
        _state.update { it.copy(dialogState = DialogState.NONE) }
    }

    private fun onAddPlayerClicked() {
        _state.update { it.copy(dialogState = DialogState.EDITING) }
    }
}

