package com.nickel.tallyscore.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import com.nickel.tallyscore.design.colorscheme.DarkColorScheme
import com.nickel.tallyscore.design.colorscheme.LightColorScheme
import com.nickel.tallyscore.design.colorscheme.TallyScoreColorScheme
import com.nickel.tallyscore.design.typography.Typography
import com.nickel.tallyscore.persistence.preferences.AppTheme
import com.nickel.tallyscore.persistence.preferences.AppThemeProvider
import com.nickel.tallyscore.ui.theme.localcompositionprovider.LocalDimensions
import com.nickel.tallyscore.ui.theme.localcompositionprovider.getDimensions

@Composable
fun TallyScoreTheme(content: @Composable () -> Unit) {
    val appTheme = AppThemeProvider.appThemeState.value
    CompositionLocalProvider(
        LocalDimensions provides getDimensions(),
        LocalColorScheme provides getColorScheme(appTheme),
        LocalTypography provides Typography,
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

private val LocalColorScheme = staticCompositionLocalOf<TallyScoreColorScheme> { LightColorScheme }
private val LocalTypography = staticCompositionLocalOf { Typography }

@Composable
private fun getColorScheme(appTheme: AppTheme) = when (appTheme) {
    AppTheme.LIGHT -> LightColorScheme
    AppTheme.DARK -> DarkColorScheme
    AppTheme.SYSTEM -> if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
}


