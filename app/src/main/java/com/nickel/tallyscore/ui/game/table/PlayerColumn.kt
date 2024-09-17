package com.nickel.tallyscore.ui.game.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun PlayerColumn(
    state: GameState,
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
        PlayerScores(
            player = player,
            itemHeight = itemHeight,
            onInteraction = onInteraction
        )
        PlayerAddScoreButton(
            playerId = player.id,
            itemHeight = itemHeight,
            onInteraction = onInteraction
        )
        repeat(player.missingTurns) {
            Spacer(Modifier.height(itemHeight))
        }
        if (state.showTotals) {
            PlayerTotalScore(
                totalScore = player.totalScore,
                itemHeight = itemHeight
            )
        }
        if (state.showPlacements) {
            PlayerPlacement(
                placement = player.placement,
                itemHeight = itemHeight
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerScores(
    player: Player,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    player.scores.forEachIndexed { index, score ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .height(itemHeight)
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
            TallyScoreText(
                text = score.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
private fun PlayerAddScoreButton(
    playerId: Long,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    TallyScoreIconButton(
        imageVector = Icons.Default.Add,
        onClick = { onInteraction(GameInteraction.AddScoreClicked(playerId)) },
        modifier = modifier
            .height(itemHeight)
            .wrapContentSize(Alignment.Center)
            .scale(0.7f)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
    )
}

@Composable
private fun PlayerTotalScore(
    totalScore: Int,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
) {
    TallyScoreText(
        text = "$totalScore",
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .height(itemHeight)
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
private fun PlayerPlacement(
    placement: Int,
    itemHeight: Dp,
    modifier: Modifier = Modifier,
) {
    TallyScoreText(
        text = "$placement",
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .height(itemHeight)
            .wrapContentSize(Alignment.Center)
    )
}

@Preview
@Composable
private fun PlayerColumnPreview() {
    TallyScoreTheme {
        val player =  Player(
            name = "Lukas",
            scores = listOf(10, 20, 30, 40)
        )
        PlayerColumn(
            state = GameState(
                players = listOf(player)
            ),
            player = player,
            itemHeight = 48.dp
        )
    }
}