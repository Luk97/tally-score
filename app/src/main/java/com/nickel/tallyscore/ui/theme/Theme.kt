package com.nickel.tallyscore.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf


@Composable
fun TallyScoreTheme(
    darkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorScheme provides if (darkMode) DarkColorScheme else LightColorScheme,
        LocalDarkModeEnabled provides darkMode,
        content = content
    )
}

object TallyScoreTheme {
    val colorScheme: TallyScoreColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

private val LocalDarkModeEnabled = staticCompositionLocalOf { false }
private val LocalTypography = staticCompositionLocalOf { Typography }
private val LocalColorScheme = staticCompositionLocalOf<TallyScoreColorScheme> { LightColorScheme }