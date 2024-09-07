package com.nickel.tallyscore.ui.editing

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class EditPlayerViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(EditState())
    val state = _state.asStateFlow()

    fun onInteraction(interaction: EditInteraction) {
        when (interaction) {
            is EditInteraction.NameChanged -> onNameChanged(interaction.name)
            is EditInteraction.ScoreChanged -> onScoreChanged(interaction.score)
        }
    }

    private fun onNameChanged(name: String) {
        _state.update { it.copy(name = name) }
    }

    private fun onScoreChanged(scoreText: String) {
        scoreText.toIntOrNull()?.let { score ->
            _state.update { it.copy(score = score) }
        }

    }
}