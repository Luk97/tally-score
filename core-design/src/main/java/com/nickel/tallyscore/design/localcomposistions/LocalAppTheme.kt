package com.nickel.tallyscore.design.localcomposistions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppTheme = staticCompositionLocalOf { Theme() }

data class Theme(val theme: AppTheme = AppTheme.SYSTEM)

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM
}

@Composable
internal fun getAppTheme() = Theme(
    theme = when {
        isSystemInDarkTheme() -> AppTheme.DARK
        else -> AppTheme.LIGHT
    }
)