package com.nickel.colorscheme

import androidx.compose.ui.graphics.Color

internal object LightColorScheme: TallyScoreColorScheme {
    override val primary: Color = Pink
    override val onPrimary: Color = Black
    override val background: Color = DarkGrey
    override val onBackground: Color = White
    override val surface: Color = MediumGrey
    override val surfaceVariant: Color = MediumGrey.copy(alpha = 0.5f)
    override val surfaceContainer: Color = MediumGrey
    override val onSurface: Color = White
    override val onSurfaceVariant: Color = White.copy(alpha = 0.5f)
    override val border: Color = Black
}