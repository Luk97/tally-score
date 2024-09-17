package com.nickel.tallyscore.ui.game.table

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.game.GameState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun LabelColumn(
    state: GameState,
    itemHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        (1..state.turnCount).forEach {
            Text(
                text = "$it",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(itemHeight)
                    .wrapContentSize(Alignment.Center)
            )
        }
        Spacer(Modifier.height(itemHeight))
        if (state.showTotals) {
            TallyScoreText(
                text = "Total",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(itemHeight)
                    .wrapContentSize(Alignment.Center)
            )
        }
        if (state.showPlacements) {
            TallyScoreText(
                text = "Place",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .height(itemHeight)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun LabelColumnPreview() {
    TallyScoreTheme {
        LabelColumn(
            state = GameState(
                players = listOf(
                    Player("Lukas")
                )
            ),
            itemHeight = 48.dp
        )
    }
}