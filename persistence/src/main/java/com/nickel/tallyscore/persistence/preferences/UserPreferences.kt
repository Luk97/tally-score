package com.nickel.tallyscore.persistence.preferences

import com.nickel.tallyscore.localcomposistions.AppTheme

data class UserPreferences(
    val zoomLevel: Float = 1f,
    val appTheme: AppTheme = AppTheme.SYSTEM
)
