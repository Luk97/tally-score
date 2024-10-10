package com.nickel.tallyscore.persistence.preferences

import kotlinx.coroutines.flow.Flow

internal interface PreferenceProvider {

    val userPreferences: Flow<UserPreferences>

    suspend fun updateZoomLevel(zoomLevel: Float)

    suspend fun updateAppTheme(appTheme: AppTheme)
}