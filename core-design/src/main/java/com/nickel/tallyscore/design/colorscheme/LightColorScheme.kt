package com.nickel.tallyscore.design.colorscheme

import androidx.compose.ui.graphics.Color

internal object LightColorScheme: TallyScoreColorScheme {
    override val primary: Color = Pink
    override val onPrimary: Color = Black
    override val background: Color = White
    override val onBackground: Color = DarkGrey
    override val surface: Color = White
    override val surfaceVariant: Color = White.copy(alpha = 0.5f)
    override val surfaceContainer: Color = White
    override val onSurface: Color = DarkGrey
    override val onSurfaceVariant: Color = DarkGrey.copy(alpha = 0.5f)
    override val border: Color = DarkGrey
}