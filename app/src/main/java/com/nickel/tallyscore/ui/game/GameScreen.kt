package com.nickel.tallyscore.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.AddPlayerButton
import com.nickel.tallyscore.ui.components.TallyScoreTopBar
import com.nickel.tallyscore.ui.dialogs.AddPlayerDialog
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
        topBar = { TallyScoreTopBar(onInteraction = onInteraction) },
        floatingActionButton = {
            AddPlayerButton(
                onAddPlayerClicked = { onInteraction(GameInteraction.AddPlayerClicked) }
            )
        }
    ) { innerPadding ->
        ScreenContent(
            state = state,
            onInteraction = onInteraction,
            modifier = Modifier.padding(innerPadding)
        )

        GameDialogs(
            dialogState = state.dialogState,
            onInteraction = onInteraction
        )
    }
}

@Composable
private fun ScreenContent(
    state: GameState,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TitleRow(state = state)
    }
}

@Composable
private fun TitleRow(
    state: GameState,
    modifier: Modifier = Modifier
) {
    // TODO: grid with players and their scores
}

@Composable
private fun GameDialogs(
    dialogState: GameState.DialogState,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    when (dialogState) {
        is GameState.DialogState.AddingPlayer -> AddPlayerDialog(
            state = dialogState,
            onInteraction = onInteraction
        )
        else -> {}
    }
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
    TallyScoreTheme {
        GameScreen(state = GameState(
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
                )
            )
        ))
    }
}