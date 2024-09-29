package com.nickel.tallyscore.ui.game.board

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nickel.tallyscore.ui.game.GameState

@Composable
fun LabelColumn(
    state: GameState,
    verticalScrollState: ScrollState,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TableCell(
            text = "Turn",
            modifier = cellModifier
        )

        ScrollableLabelColumn(
            state = state,
            Modifier.verticalScroll(verticalScrollState),
            cellModifier = cellModifier
        )
    }
}

@Composable
private fun ScrollableLabelColumn(
    state: GameState,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        (1..state.turnCount).forEach {
            TableCell(
                text = "$it",
                modifier = cellModifier
            )
        }

        Spacer(cellModifier)

        if (state.showTotals) {
            TableCell(
                text = "Total",
                modifier = cellModifier
            )
        }

        if (state.showPlacements) {
            TableCell(
                text = "Place",
                modifier = cellModifier
            )
        }
    }
}