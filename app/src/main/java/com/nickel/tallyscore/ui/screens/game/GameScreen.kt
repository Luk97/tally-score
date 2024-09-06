package com.nickel.tallyscore.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun GameScreen(viewModel: GameScreenViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    GameScreen(state = state)
}

@Composable
private fun GameScreen(state: GameState) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Red))
}

@Preview
@Composable
private fun GameScreenPreview() {
    TallyScoreTheme {
        GameScreen(state = GameState())
    }
}