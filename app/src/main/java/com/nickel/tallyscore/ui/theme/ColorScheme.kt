package com.nickel.tallyscore.ui.theme

import androidx.compose.ui.graphics.Color

interface TallyScoreColorScheme {
    val primary: Color
    val onPrimary: Color
    val background: Color
    val onBackground: Color
    val surface: Color
    val surfaceContainer: Color
    val onSurface: Color
    val onSurfaceVariant: Color
}

object LightColorScheme: TallyScoreColorScheme {
    override val primary: Color = Pink
    override val onPrimary: Color = Black
    override val background: Color = DarkGrey
    override val onBackground: Color = White
    override val surface: Color = MediumGrey
    override val surfaceContainer: Color = MediumGrey
    override val onSurface: Color = White
    override val onSurfaceVariant: Color = White.copy(alpha = 0.5f)
}

object DarkColorScheme: TallyScoreColorScheme {
    override val primary: Color = Pink
    override val onPrimary: Color = Black
    override val background: Color = DarkGrey
    override val onBackground: Color = White
    override val surface: Color = MediumGrey
    override val surfaceContainer: Color = MediumGrey
    override val onSurface: Color = White
    override val onSurfaceVariant: Color = White.copy(alpha = 0.5f)
}
