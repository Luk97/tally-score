package com.nickel.tallyscore.ui.game.table

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
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
        (screenWidth / 4).coerceAtLeast(64)
    }
    val turnColumnWidth = remember(playerColumnWidth) {
        playerColumnWidth / 1.5
    }
    val cellHeight = remember(playerColumnWidth) {
        playerColumnWidth / 2
    }
    val tableWidth = remember(state.players.size, turnColumnWidth, playerColumnWidth) {
        state.players.size * playerColumnWidth + turnColumnWidth + 17
    }
    val columnHeight = remember(state.columnItemCount, cellHeight) {
        state.columnItemCount * cellHeight
    }
    val verticalScrollState = rememberScrollState()

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.showLabels) {
            LabelColumn(
                state = state,
                cellHeight = cellHeight.dp,
                verticalScrollState = verticalScrollState,
                modifier = Modifier.size(
                    width = turnColumnWidth.dp,
                    height = columnHeight.dp
                )
            )
            Box {
                VerticalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .height(columnHeight.dp)
                        .padding(horizontal = 8.dp)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .width(17.dp)
                        .offset(y = cellHeight.dp)
                )
            }
        }

        PlayerList(
            state = state,
            cellHeight = cellHeight.dp,
            playerColumnWidth = playerColumnWidth.dp,
            onInteraction = onInteraction,
            verticalScrollState = verticalScrollState,
            modifier = Modifier.size(
                width = tableWidth.dp,
                height = columnHeight.dp
            )
        )
    }
}

@Composable
private fun PlayerList(
    state: GameState,
    cellHeight: Dp,
    playerColumnWidth: Dp,
    verticalScrollState: ScrollState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    LazyRow(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier
    ) {
        items(state.players) { player ->
            PlayerColumn(
                state = state,
                player = player,
                cellHeight = cellHeight,
                verticalScrollState = verticalScrollState,
                modifier = Modifier.width(playerColumnWidth),
                onInteraction = onInteraction
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
                    )
                )
            )
        )
    }
}