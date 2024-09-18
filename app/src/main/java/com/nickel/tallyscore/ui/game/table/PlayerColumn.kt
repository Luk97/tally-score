package com.nickel.tallyscore.ui.game.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayerColumn(
    state: GameState,
    player: Player,
    itemHeight: Dp,
    verticalScrollState: ScrollState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        TableCell(
            text = player.name,
            modifier = Modifier
                .height(itemHeight)
                .combinedClickable(
                    onClick = { onInteraction(GameInteraction.EditPlayerClicked(player)) },
                    onLongClick = { onInteraction(GameInteraction.DeletePlayerClicked(player)) }
                )
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
        )

        ScrollablePlayerColumn(
            state = state,
            player = player,
            itemHeight = itemHeight,
            onInteraction = onInteraction,
            modifier = Modifier.verticalScroll(verticalScrollState)
        )
    }
}

@Composable
private fun ScrollablePlayerColumn(
    state: GameState,
    player: Player,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(modifier = modifier) {
        PlayerScores(
            player = player,
            onInteraction = onInteraction,
            modifier = Modifier.height(itemHeight)
        )

        AddPlayerScoreCell(
            playerId = player.id,
            onInteraction = onInteraction,
            modifier = Modifier.height(itemHeight)
        )

        repeat(player.missingTurns) {
            Spacer(Modifier.height(itemHeight))
        }

        if (state.showTotals) {
            TableCell(
                text = "${player.totalScore}",
                modifier = Modifier.height(itemHeight)
            )
        }

        if (state.showPlacements) {
            TableCell(
                text = "${player.placement}",
                modifier = Modifier.height(itemHeight)
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
                            playerId = player.id,
                            score = "$score",
                            index = index
                        )
                    )
                },
                onLongClick = {
                    onInteraction(
                        GameInteraction.DeleteScoreClicked(
                            playerId = player.id,
                            index = index
                        )
                    )
                }
            )
        )
    }
}