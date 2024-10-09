package com.nickel.tallyscore.persistence.preferences

import com.nickel.tallyscore.localcomposistions.AppTheme
import kotlinx.coroutines.flow.Flow

interface PreferenceProvider {

    val userPreferences: Flow<UserPreferences>

    suspend fun updateZoomLevel(zoomLevel: Float)

    suspend fun updateAppTheme(appTheme: AppTheme)
}