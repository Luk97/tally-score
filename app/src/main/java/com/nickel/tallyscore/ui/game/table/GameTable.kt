package com.nickel.tallyscore.ui.game.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun GameTable(
    state: GameState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    val screenPadding: Int = remember { 32 }
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
    val tableWidth = remember(state.players.size, turnColumnWidth, playerColumnWidth) {
        state.players.size * playerColumnWidth + turnColumnWidth + 17
    }
    val columnHeight = remember(state.columnItemCount, itemHeight) {
        state.columnItemCount * itemHeight
    }

    // TODO: synchronize both lazy rows

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        if (state.showLabels) {
            HeaderRow(
                players = state.players,
                turnColumnWidth = turnColumnWidth.dp,
                playerColumnWidth = playerColumnWidth.dp,
                itemHeight = itemHeight.dp,
                showLabels = state.showLabels,
                onInteraction = onInteraction
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.width(tableWidth.dp)
            )
        }
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .verticalScroll(rememberScrollState())
                .height(columnHeight.dp)
        ) {
            if (state.showLabels) {
                LabelColumn(
                    state = state,
                    itemHeight = itemHeight.dp,
                    modifier = Modifier.width(turnColumnWidth.dp)
                )
                VerticalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                )
            } else {
                Spacer(Modifier.width(turnColumnWidth.dp))
            }
            LazyRow(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                items(state.players) { player ->
                    PlayerColumn(
                        state = state,
                        player = player,
                        itemHeight = itemHeight.dp,
                        modifier = Modifier.width(playerColumnWidth.dp),
                        onInteraction = onInteraction
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HeaderRow(
    players: List<Player>,
    turnColumnWidth: Dp,
    playerColumnWidth: Dp,
    itemHeight: Dp,
    showLabels: Boolean,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(itemHeight)
    ) {
        if (showLabels) {
            TallyScoreText(
                text = "Turn",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .width(turnColumnWidth)
                    .height(itemHeight)
                    .wrapContentSize(Alignment.Center)
            )
            VerticalDivider(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            )
        } else {
            Spacer(Modifier.width(turnColumnWidth))
        }
        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            items(players) { player ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .width(playerColumnWidth)
                        .height(itemHeight)
                        .clip(RoundedCornerShape(8.dp))
                        .combinedClickable(
                            onClick = { onInteraction(GameInteraction.EditPlayerClicked(player)) },
                            onLongClick = { onInteraction(GameInteraction.DeletePlayerClicked(player)) }
                        )
                ) {
                    TallyScoreText(
                        text = player.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
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
                    )
                )
            )
        )
    }
}