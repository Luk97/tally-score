package com.nickel.tallyscore.ui.game.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.nickel.tallyscore.R
import com.nickel.tallyscore.player.Player
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState
import com.nickel.tallyscore.TallyScoreTheme
import com.nickel.tallyscore.ui.theme.localcompositionprovider.LocalDimensions

@Composable
internal fun GameBoard(
    state: GameState,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, TallyScoreTheme.colorScheme.border),
        modifier = Modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(8.dp),
                    brush = Brush.linearGradient(
                        colors = listOf(
                            TallyScoreTheme.colorScheme.surface,
                            TallyScoreTheme.colorScheme.surfaceVariant
                        ),
                    ),
                )
        ) {
            GameBoardContent(state, onInteraction)
            GameBoardDivider(state)
        }
    }
}

@Composable
private fun GameBoardContent(
    state: GameState,
    onInteraction: (GameInteraction) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
    ) {
        HeaderColumn(
            state = state,
            scrollState = scrollState
        )

        LazyRow(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
        ) {
            items(state.players) { player ->
                PlayerColumn(
                    state = state,
                    player = player,
                    scrollState = scrollState,
                    onInteraction = onInteraction
                )
            }
        }
    }
}

@Composable
private fun HeaderColumn(
    state: GameState,
    scrollState: ScrollState,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextBoardCell(
            text = stringResource(R.string.turn),
            zoomLevel = state.preferences.zoomLevel
        )

        ScrollableHeaderColumn(
            state = state,
            modifier = Modifier.verticalScroll(scrollState)
        )
    }
}

@Composable
private fun ScrollableHeaderColumn(
    state: GameState,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        (1..state.turnCount).forEach {
            TextBoardCell(
                text = "$it",
                zoomLevel = state.preferences.zoomLevel
            )
        }

        EmptyBoardCell(state.preferences.zoomLevel)

        if (state.showTotals) {
            TextBoardCell(
                text = stringResource(R.string.total),
                zoomLevel = state.preferences.zoomLevel
            )
        }

        if (state.showPlacements) {
            TextBoardCell(
                text = stringResource(R.string.placement),
                zoomLevel = state.preferences.zoomLevel
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerColumn(
    state: GameState,
    player: Player,
    scrollState: ScrollState,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextBoardCell(
            text = player.name,
            zoomLevel = state.preferences.zoomLevel,
            modifier = Modifier.combinedClickable(
                onClick = { onInteraction(GameInteraction.EditPlayerClicked(player)) },
                onLongClick = { onInteraction(GameInteraction.DeletePlayerClicked(player)) }
            )
        )

        ScrollablePlayerColumn(
            state = state,
            player = player,
            onInteraction = onInteraction,
            modifier = Modifier.verticalScroll(scrollState)
        )
    }
}

@Composable
private fun ScrollablePlayerColumn(
    state: GameState,
    player: Player,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(modifier = modifier) {
        PlayerScores(
            player = player,
            zoomLevel = state.preferences.zoomLevel,
            onInteraction = onInteraction,
        )

        AddScoreBoardCell(
            player = player,
            zoomLevel = state.preferences.zoomLevel,
            onInteraction = onInteraction,
        )

        repeat(player.missingTurns) {
            EmptyBoardCell(state.preferences.zoomLevel)
        }

        if (state.showTotals) {
            TextBoardCell(
                text = "${player.totalScore}",
                zoomLevel = state.preferences.zoomLevel
            )
        }

        if (state.showPlacements) {
            TextBoardCell(
                text = "${player.placement}",
                zoomLevel = state.preferences.zoomLevel
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerScores(
    player: Player,
    zoomLevel: Float,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    player.scores.forEachIndexed { index, score ->
        TextBoardCell(
            text = "$score",
            zoomLevel = zoomLevel,
            modifier = Modifier.combinedClickable(
                onClick = { onInteraction(GameInteraction.EditScoreClicked(player, index)) },
                onLongClick = { onInteraction(GameInteraction.DeleteScoreClicked(player, index)) }
            )
        )
    }
}

@Composable
private fun GameBoardDivider(state: GameState) {
    val dimensions = LocalDimensions.current
    val cellWidth = dimensions.cellWidth * state.preferences.zoomLevel
    val cellHeight = dimensions.cellHeight * state.preferences.zoomLevel
    VerticalDivider(
        color = TallyScoreTheme.colorScheme.border,
        modifier = Modifier
            .offset(x = cellWidth)
            .height(state.verticalItemCount * cellHeight)
    )
    HorizontalDivider(
        color = TallyScoreTheme.colorScheme.border,
        modifier = Modifier
            .offset(y = cellHeight)
            .width(state.horizontalItemCount * cellWidth)
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