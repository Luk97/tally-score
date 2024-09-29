package com.nickel.tallyscore.ui.game

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.tallyscore.core.snackbar.ObserveAsEvents
import com.nickel.tallyscore.core.snackbar.SnackBarController
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.AddPlayerButton
import com.nickel.tallyscore.ui.dialogs.AddPlayerDialog
import com.nickel.tallyscore.ui.dialogs.AddScoreDialog
import com.nickel.tallyscore.ui.dialogs.EditPlayerDialog
import com.nickel.tallyscore.ui.dialogs.EditScoreDialog
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.ui.game.board.GameBoard
import com.nickel.tallyscore.ui.game.topbar.GameTopBar
import com.nickel.tallyscore.ui.theme.TallyScoreTheme
import kotlinx.coroutines.launch

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
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(
        flow = SnackBarController.events,
        key1 = snackBarHostState
    ) { event ->
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            val result = snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.label,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        topBar = { GameTopBar() },
        floatingActionButton = {
            AddPlayerButton(
                onAddPlayerClicked = { onInteraction(GameInteraction.AddPlayerClicked) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        GameBoard(
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
private fun GameDialogs(
    dialogState: DialogState,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    when (dialogState) {
        is DialogState.AddingPlayer -> AddPlayerDialog(
            onInteraction = onInteraction
        )
        is DialogState.EditingPlayer -> EditPlayerDialog(
            player = dialogState.player,
            onInteraction = onInteraction
        )
        is DialogState.AddingScore -> AddScoreDialog(
            player = dialogState.player,
            onInteraction = onInteraction
        )
        is DialogState.EditingScore -> EditScoreDialog(
            player = dialogState.player,
            scoreIndex = dialogState.index,
            onInteraction = onInteraction
        )
        else -> {}
    }
}

@Preview(showSystemUi = true)
@Composable
private fun GameScreenPreview() {
    TallyScoreTheme {
        GameScreen(
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