package com.nickel.localcomposistions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.nickel.colorscheme.DarkColorScheme
import com.nickel.colorscheme.LightColorScheme
import com.nickel.colorscheme.TallyScoreColorScheme

internal val LocalColorScheme = staticCompositionLocalOf<TallyScoreColorScheme> { LightColorScheme }

@Composable
internal fun getColorScheme() =
    if (LocalAppTheme.current.theme == AppTheme.DARK) {
        DarkColorScheme
    } else LightColorScheme
