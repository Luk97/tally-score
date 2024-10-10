package com.nickel.tallyscore.persistence.preferences

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object AppThemeProvider {
    var appThemeState: MutableState<AppTheme> = mutableStateOf(AppTheme.SYSTEM)
        private set

    internal var appTheme: AppTheme
        get() = appThemeState.value
        set(value) {
            appThemeState.value = value
        }
}