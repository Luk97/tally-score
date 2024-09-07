package com.nickel.tallyscore.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
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
                onSettingsClicked = { onInteraction(GameInteraction.SettingsClicked) })
        },
        //windowInsets = WindowInsets.statusBars,
        modifier = modifier
    )
}

@Composable
private fun TopBarActions(
    onInfoClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {}
) {
    IconButton(
        onClick = onInfoClicked
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
    Spacer(Modifier.width(8.dp))
    IconButton(
            onClick = onSettingsClicked
            ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    TallyScoreTheme {
        TopBar()
    }
}