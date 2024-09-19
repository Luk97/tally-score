package com.nickel.tallyscore.ui.topbar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameTopBarViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(GameTopBarState())
    val state = _state.asStateFlow()

    fun onInteraction(interaction: GameTopBarInteraction) {
        when (interaction) {
            GameTopBarInteraction.MenuClicked -> onMenuClicked()
            GameTopBarInteraction.MenuDismissed -> onMenuDismissed()
        }
    }

    private fun onMenuClicked() {
        _state.update { it.copy(showMenu = true) }
    }

    private fun onMenuDismissed() {
        _state.update { it.copy(showMenu = false) }
    }
}