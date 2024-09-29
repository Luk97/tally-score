package com.nickel.tallyscore.ui.game.board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun GameBoard(
    state: GameState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val columnWidth = remember(screenWidth) {
        (screenWidth / 5).coerceAtLeast(64)
    }
    val cellHeight = remember(columnWidth) { columnWidth / 2 }
    val tableWidth = remember(state.players.size, columnWidth) {
        state.players.size * columnWidth + columnWidth
    }
    val tableHeight = remember(state.columnItemCount, cellHeight) {
        state.columnItemCount * cellHeight
    }
    val cellModifier = Modifier.size(
        width = columnWidth.dp,
        height = cellHeight.dp
    )

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.size(
            width = tableWidth.dp,
            height = tableHeight.dp
        )
    ) {
        GameBoardContent(
            state = state,
            cellModifier = cellModifier,
            onInteraction = onInteraction
        )

        if (state.gameBoardVisible) {
            GameBoardDivider(
                horizontalOffset = columnWidth,
                verticalOffset = cellHeight
            )
        }
    }
}

@Composable
fun GameBoardContent(
    state: GameState,
    modifier: Modifier = Modifier,
    cellModifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {},
) {
    val verticalScrollState = rememberScrollState()

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        if (state.gameBoardVisible) {
            LabelColumn(
                state = state,
                verticalScrollState = verticalScrollState,
                cellModifier = cellModifier
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            items(state.players) { player ->
                PlayerColumn(
                    state = state,
                    player = player,
                    verticalScrollState = verticalScrollState,
                    cellModifier = cellModifier,
                    onInteraction = onInteraction
                )
            }
        }
    }
}

@Composable
fun GameBoardDivider(
    horizontalOffset: Int,
    verticalOffset: Int
) {
    VerticalDivider(
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .offset(x = horizontalOffset.dp)
            .fillMaxSize()
    )
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .offset(y = verticalOffset.dp)
            .fillMaxSize()
    )
}

@Preview
@Composable
private fun GameTablePreview() {
    TallyScoreTheme {
        GameBoard(
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