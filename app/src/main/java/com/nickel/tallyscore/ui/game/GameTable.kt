package com.nickel.tallyscore.ui.game

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun GameTable(
    state: GameState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    val screenPadding = 32
    val screenWidth = LocalConfiguration.current.screenWidthDp - screenPadding


    val playerColumnWidth = remember(screenWidth) {
        (screenWidth / 5).coerceAtLeast(64)
    }
    val turnColumnWidth = remember(playerColumnWidth) {
        playerColumnWidth / 1.5
    }
    val itemHeight = remember(playerColumnWidth) {
        playerColumnWidth / 2
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            item {
                if (state.showTurns) {
                    TurnColumn(
                        turnCount = state.turnCount,
                        itemHeight = itemHeight.dp,
                        modifier = Modifier.width(turnColumnWidth.dp)
                    )
                } else {
                    Spacer(Modifier.width(turnColumnWidth.dp))
                }
            }

            items(state.players) { player ->
                PlayerColumn(
                    player = player,
                    itemHeight = itemHeight.dp,
                    modifier = Modifier.width(playerColumnWidth.dp),
                    onInteraction = onInteraction
                )
            }
        }
        if (state.showTotals) {
            TotalScoreRow(
                players = state.players,
                titleWidth = turnColumnWidth.dp,
                itemWidth = playerColumnWidth.dp,
                modifier = Modifier.height(itemHeight.dp)
            )
        }
        if (state.showPlacements) {
            PlacementsRow(
                placements = state.placements,
                titleWidth = turnColumnWidth.dp,
                itemWidth = playerColumnWidth.dp,
                modifier = Modifier.height(itemHeight.dp)
            )
        }
    }
}

@Composable
private fun TurnColumn(
    turnCount: Int,
    itemHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Turn",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .height(itemHeight)
                .wrapContentSize(Alignment.Center)
        )
        (1..turnCount).forEach {
            Text(
                text = "$it",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(itemHeight)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerColumn(
    player: Player,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .combinedClickable(
                    onClick = { onInteraction(GameInteraction.EditPlayerClicked(player)) },
                    onLongClick = { onInteraction(GameInteraction.DeletePlayerClicked(player)) }
                )
        ) {
            Text(
                text = player.name,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                modifier = Modifier
                    .height(itemHeight)
                    .wrapContentSize(Alignment.Center)
            )
        }

        player.scores.forEachIndexed { index, score ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .combinedClickable(
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
            ) {
                Text(
                    text = score.toString(),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .height(itemHeight)
                        .wrapContentSize(Alignment.Center)
                )
            }
        }

        TallyScoreIconButton(
            imageVector = Icons.Default.Add,
            onClick = { onInteraction(GameInteraction.AddScoreClicked(player.id)) },
            modifier = modifier
                .height(itemHeight)
                .wrapContentSize(Alignment.Center)
                .scale(0.7f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun TotalScoreRow(
    players: List<Player>,
    titleWidth: Dp,
    itemWidth: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Text(
            text = "Total",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .width(titleWidth)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        players.forEach {
            Text(
                text = "${it.totalScore}",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .width(itemWidth)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun PlacementsRow(
    placements: List<Int>,
    titleWidth: Dp,
    itemWidth: Dp,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Text(
            text = "Placement",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .width(titleWidth)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        placements.forEach {
            Text(
                text = "$it.",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .width(itemWidth)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
private fun GameTablePreview() {
    TallyScoreTheme {
        GameTable(
            state = GameState(
                players = listOf(
                    Player(
                        name = "Lukas",
                        scores = listOf(10, 10, 20, 20)
                    ),
                    Player(
                        name = "Linda",
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Maria",
                        scores = listOf(10, 10, 10, 20)
                    ),
                    Player(
                        name = "Anne",
                        scores = listOf(10, 30, 20, 20)
                    )
                )
            )
        )
    }
}