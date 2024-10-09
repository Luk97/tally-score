package com.nickel.tallyscore.localcomposistions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.nickel.tallyscore.colorscheme.DarkColorScheme
import com.nickel.tallyscore.colorscheme.LightColorScheme
import com.nickel.tallyscore.colorscheme.TallyScoreColorScheme

internal val LocalColorScheme = staticCompositionLocalOf<TallyScoreColorScheme> { LightColorScheme }

@Composable
internal fun getColorScheme() =
    if (LocalAppTheme.current.theme == AppTheme.DARK) {
        DarkColorScheme
    } else LightColorScheme
