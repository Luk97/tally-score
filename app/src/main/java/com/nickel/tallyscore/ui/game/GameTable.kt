package com.nickel.tallyscore.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
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

    LazyRow(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            TurnColumn(
                turnCount = state.turnCount,
                modifier = Modifier.width(turnColumnWidth.dp)
            )
        }
        items(state.players) { player ->
            PlayerColumn(
                player = player,
                modifier = Modifier.width(playerColumnWidth.dp),
                onInteraction = onInteraction
            )
        }
    }
}

@Composable
private fun TurnColumn(turnCount: Int, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "Turn",
            color = MaterialTheme.colorScheme.onBackground
        )
        (1..turnCount).forEach {
            Text(
                text = "$it",
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun PlayerColumn(
    player: Player,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = player.name, color = MaterialTheme.colorScheme.onBackground)
        player.scores.forEach { score ->
            Text(text = score.toString(), color = MaterialTheme.colorScheme.onBackground)
        }

        AddScoreButton(
            playerId = player.id,
            onClick = { onInteraction(GameInteraction.AddScoreClicked(it)) }
        )

    }
}

@Composable
private fun AddScoreButton(
    playerId: Long,
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit = {}
) {
    IconButton(
        onClick = { onClick(playerId) },
        modifier = modifier
            .scale(0.5f)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary
        )
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
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Linda",
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Maria",
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Anne",
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Peter",
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Peter",
                        scores = listOf(10, 30, 20, 20)
                    ),
                    Player(
                        name = "Peter",
                        scores = listOf(10, 30, 20, 20)
                    )
                )
            )
        )
    }
}