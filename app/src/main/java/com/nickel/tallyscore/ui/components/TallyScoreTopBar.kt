package com.nickel.tallyscore.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TallyScoreTopBar(
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    TopAppBar(
        title = {
            Text("Tally Score")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            TopBarActions(
                onInfoClicked = { onInteraction(GameInteraction.InfoClicked) },
                onResetClicked = { onInteraction(GameInteraction.ResetClicked) },
                onDeleteClicked = { onInteraction(GameInteraction.DeleteClicked) }
            )
        },
        modifier = modifier
    )
}

@Composable
private fun TopBarActions(
    onInfoClicked: () -> Unit = {},
    onResetClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {}
) {
    TallyScoreIconButton(
        imageVector = Icons.Default.Info,
        onClick = onInfoClicked,
        modifier = Modifier.scale(1.5f)
    )
    Spacer(Modifier.width(8.dp))
    TallyScoreIconButton(
        imageVector = Icons.Default.Replay,
        onClick = onResetClicked,
        modifier = Modifier.scale(1.5f)
    )
    Spacer(Modifier.width(8.dp))
    TallyScoreIconButton(
        imageVector = Icons.Default.DeleteForever,
        onClick = onDeleteClicked,
        modifier = Modifier.scale(1.5f)
    )
}

@Preview
@Composable
private fun TallyScoreTopBarPreview() {
    TallyScoreTheme {
        TallyScoreTopBar()
    }
}