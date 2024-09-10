package com.nickel.tallyscore.ui.editing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.datastore.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPlayerViewModel @Inject constructor(
    private val repository: PlayerRepository
): ViewModel() {

    private val _state = MutableStateFlow(EditState())
    val state = _state.asStateFlow()

    fun onInteraction(interaction: EditInteraction) {
        when (interaction) {
            is EditInteraction.NameChanged -> onNameChanged(interaction.name)
            is EditInteraction.ScoreChanged -> onScoreChanged(interaction.score)
            EditInteraction.AddPlayerClicked -> onAddPlayerClicked()
        }
    }

    private fun onNameChanged(value: String) {
        _state.update { it.copy(name = value) }
    }



    private fun onScoreChanged(value: String) {
        val score = value
            .filter { it.isDigit() }
            .toIntOrNull()
            ?.toString()
            ?: value
        _state.update { it.copy(score = score) }
    }

    private fun onAddPlayerClicked() {
        viewModelScope.launch {
            _state.value.score.toInt().let {
                val player = Player(name = _state.value.name, score = it)
                repository.upsertPlayer(player)
            }
        }
        _state.update { it.copy(name = "", score = "0") }
    }
}