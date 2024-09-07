package com.nickel.tallyscore.ui.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.tallyscore.ui.components.AddPlayerButton
import com.nickel.tallyscore.ui.components.TopBar
import com.nickel.tallyscore.ui.editing.EditPlayerDialog
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun GameScreen(viewModel: GameScreenViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    GameScreen(state = state, onInteraction = viewModel::onInteraction)
}

@Composable
private fun GameScreen(
    state: GameState,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Scaffold(
        topBar = { TopBar(onInteraction = onInteraction) },
        floatingActionButton = {
            AddPlayerButton(
                onAddPlayerClicked = { onInteraction(GameInteraction.AddPlayerClicked) }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            ScreenContent(
                state = state,
                onInteraction = onInteraction
            )
        }
    }
}

@Composable
private fun ScreenContent(
    state: GameState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Box(modifier = modifier) {
        when (state.dialogState) {
            GameState.DialogState.EDITING -> EditPlayerDialog(
                onDismiss = { onInteraction(GameInteraction.DialogDismissed) }
            )
            else -> {}
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
    TallyScoreTheme {
        GameScreen(state = GameState())
    }
}