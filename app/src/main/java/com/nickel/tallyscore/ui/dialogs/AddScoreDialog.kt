package com.nickel.tallyscore.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nickel.tallyscore.ui.components.TallyScoreTextField
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun AddScoreDialog(
    state: DialogState.AddingScore,
    onInteraction: (GameInteraction) -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(onDismissRequest = { onInteraction(GameInteraction.DialogDismissed) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Text(
                text = "Add Score",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TallyScoreTextField(
                text = state.score,
                onValueChange = { onInteraction(GameInteraction.ScoreChanged(it)) },
                label = "Score",
                placeHolder = "Your score...",
                keyboardType = KeyboardType.Number,
                maxChars = 6,
                modifier = Modifier
                    .padding(16.dp)
                    .focusRequester(focusRequester)
            )
            Button(
                onClick = { onInteraction(GameInteraction.DialogConfirmed) },
                enabled = state.validInput,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Add Score")
            }
        }
    }
}

@Preview
@Composable
private fun AddScoreDialogPreview() {
    TallyScoreTheme {
        AddScoreDialog(state = DialogState.AddingScore(playerId = 1L, score = "45"))
    }
}