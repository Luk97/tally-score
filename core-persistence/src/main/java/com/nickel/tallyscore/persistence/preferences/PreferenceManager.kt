package com.nickel.tallyscore.persistence.preferences

import kotlinx.coroutines.flow.Flow

object PreferenceManager {

    private val provider: PreferenceProvider = PreferencesDatastoreProvider()

    val userPreferences: Flow<UserPreferences> = provider.userPreferences

    suspend fun updateZoomLevel(zoomLevel: Float) {
        provider.updateZoomLevel(zoomLevel)
    }

    suspend fun updateAppTheme(appTheme: AppTheme) {
        provider.updateAppTheme(appTheme)
        AppThemeProvider.appTheme = appTheme
    }
}