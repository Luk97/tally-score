package com.nickel.tallyscore.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Pink,
    onPrimary = Black,
    onSecondary = Black,
    background = DarkGrey,
    onBackground = White,
    surface = MediumGrey,
    surfaceContainer = MediumGrey,
    onSurface = White,
    onSurfaceVariant = White.copy(alpha = 0.5f),
)

@Composable
fun TallyScoreTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}