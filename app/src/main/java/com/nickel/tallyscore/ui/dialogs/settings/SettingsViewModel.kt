package com.nickel.tallyscore.ui.dialogs.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.preferences.UserPreferences
import com.nickel.tallyscore.preferences.UserPreferences.BoardSize
import com.nickel.tallyscore.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: UserPreferencesRepository
): ViewModel() {

    val settingsState: StateFlow<SettingsState> = repository.userPreferences.map { preferences ->
        SettingsState.Success(
            settings = preferences
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = SettingsState.Loading
    )

    fun onBoardSizeClicked(boardSize: BoardSize) {
        viewModelScope.launch {
            repository.updateBoardSize(boardSize)
        }
    }

    sealed class SettingsState {
        data object Loading: SettingsState()
        data class Success(val settings: UserPreferences): SettingsState()
    }
}