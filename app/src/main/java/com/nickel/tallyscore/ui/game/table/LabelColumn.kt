package com.nickel.tallyscore.ui.game.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.nickel.tallyscore.ui.game.GameState

@Composable
fun LabelColumn(
    state: GameState,
    cellHeight: Dp,
    verticalScrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TableCell(
            text = "Turn",
            modifier = Modifier.height(cellHeight)
        )

        ScrollableLabelColumn(
            state = state,
            cellHeight = cellHeight,
            Modifier.verticalScroll(verticalScrollState)
        )
    }
}

@Composable
private fun ScrollableLabelColumn(
    state: GameState,
    cellHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        (1..state.turnCount).forEach {
            TableCell(
                text = "$it",
                modifier = Modifier.height(cellHeight)
            )
        }

        Spacer(Modifier.height(cellHeight))

        if (state.showTotals) {
            TableCell(
                text = "Total",
                modifier = Modifier.height(cellHeight)
            )
        }

        if (state.showPlacements) {
            TableCell(
                text = "Place",
                modifier = Modifier.height(cellHeight)
            )
        }
    }
}