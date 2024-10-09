package com.nickel.tallyscore.design.localcomposistions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.nickel.tallyscore.design.colorscheme.DarkColorScheme
import com.nickel.tallyscore.design.colorscheme.LightColorScheme
import com.nickel.tallyscore.design.colorscheme.TallyScoreColorScheme

internal val LocalColorScheme = staticCompositionLocalOf<TallyScoreColorScheme> { LightColorScheme }

@Composable
internal fun getColorScheme() =
    if (LocalAppTheme.current.theme == AppTheme.DARK) {
        DarkColorScheme
    } else LightColorScheme
