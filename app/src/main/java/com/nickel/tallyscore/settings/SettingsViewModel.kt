package com.nickel.tallyscore.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nickel.tallyscore.persistence.preferences.AppTheme
import com.nickel.tallyscore.persistence.preferences.PreferenceManager
import com.nickel.tallyscore.persistence.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(): ViewModel() {

    val settingsState: StateFlow<SettingsState> = PreferenceManager.userPreferences
        .map { preferences ->
            SettingsState.Success(settings = preferences)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SettingsState.Loading
        )

    fun onZoomLevelChanged(zoomLevel: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            PreferenceManager.updateZoomLevel(zoomLevel)
        }
    }

    fun onAppThemeChanged(appTheme: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            PreferenceManager.updateAppTheme(appTheme)
        }
    }

    sealed class SettingsState {
        data object Loading: SettingsState()
        data class Success(val settings: UserPreferences): SettingsState()
    }
}