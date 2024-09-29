package com.nickel.tallyscore.ui.game.board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerColumn(
    state: GameState,
    player: Player,
    verticalScrollState: ScrollState,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TableCell(
            text = player.name,
            modifier = cellModifier.combinedClickable(
                onClick = { onInteraction(GameInteraction.EditPlayerClicked(player)) },
                onLongClick = { onInteraction(GameInteraction.DeletePlayerClicked(player)) }
            )
        )

        ScrollablePlayerColumn(
            state = state,
            player = player,
            onInteraction = onInteraction,
            modifier = Modifier.verticalScroll(verticalScrollState),
            cellModifier = cellModifier
        )
    }
}

@Composable
private fun ScrollablePlayerColumn(
    state: GameState,
    player: Player,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(modifier = modifier) {
        PlayerScores(
            player = player,
            onInteraction = onInteraction,
            modifier = cellModifier
        )

        AddPlayerScoreCell(
            player = player,
            onInteraction = onInteraction,
            modifier = cellModifier
        )

        repeat(player.missingTurns) {
            Spacer(cellModifier)
        }

        if (state.showTotals) {
            TableCell(
                text = "${player.totalScore}",
                modifier = cellModifier
            )
        }

        if (state.showPlacements) {
            TableCell(
                text = "${player.placement}",
                modifier = cellModifier
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerScores(
    player: Player,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    player.scores.forEachIndexed { index, score ->
        TableCell(
            text = "$score",
            modifier = modifier.combinedClickable(
                onClick = {
                    onInteraction(
                        GameInteraction.EditScoreClicked(
                            player = player,
                            index = index
                        )
                    )
                },
                onLongClick = {
                    onInteraction(
                        GameInteraction.DeleteScoreClicked(
                            player = player,
                            index = index
                        )
                    )
                }
            )
        )
    }
}