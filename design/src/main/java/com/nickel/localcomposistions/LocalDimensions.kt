package com.nickel.tallyscore.ui.theme.localcompositionprovider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalDimensions = staticCompositionLocalOf { Dimensions.default }

data class Dimensions(
    val cellWidth: Dp,
    val cellHeight: Dp
) {
    companion object {
        val default = Dimensions(
            cellWidth = 96.dp,
            cellHeight = 32.dp)
    }
}

@Composable
internal fun getDimensions(): Dimensions {
    val boardPadding = 32
    val availableWidth = LocalConfiguration.current.screenWidthDp - boardPadding

    val cellDimensions = remember(availableWidth) {
        val cellWidth = (availableWidth / 4).coerceIn(64..128).dp
        val cellHeight = cellWidth / 3
        Dimensions(cellWidth, cellHeight)
    }

    return cellDimensions
}