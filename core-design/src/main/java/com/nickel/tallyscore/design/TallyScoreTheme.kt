package com.nickel.tallyscore.design

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.nickel.tallyscore.design.colorscheme.TallyScoreColorScheme
import com.nickel.tallyscore.design.localcomposistions.LocalAppTheme
import com.nickel.tallyscore.design.localcomposistions.LocalColorScheme
import com.nickel.tallyscore.design.localcomposistions.LocalTypography
import com.nickel.tallyscore.design.localcomposistions.getAppTheme
import com.nickel.tallyscore.design.localcomposistions.getColorScheme
import com.nickel.tallyscore.ui.theme.localcompositionprovider.LocalDimensions
import com.nickel.tallyscore.ui.theme.localcompositionprovider.getDimensions

@Composable
fun TallyScoreTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalAppTheme provides getAppTheme(),
        LocalDimensions provides getDimensions(),
        LocalColorScheme provides getColorScheme(),
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

