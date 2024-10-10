package com.nickel.tallyscore.persistence.preferences

data class UserPreferences(
    val zoomLevel: Float = 1f,
    val appTheme: AppTheme = AppTheme.SYSTEM
)
