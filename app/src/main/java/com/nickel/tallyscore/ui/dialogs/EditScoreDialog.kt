package com.nickel.tallyscore.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nickel.tallyscore.core.TallyScoreConfig
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.components.TallyScoreTextField
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun EditScoreDialog(
    state: DialogState.EditingScore,
    onInteraction: (GameInteraction) -> Unit = {},
) {
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
                text = "Edit Score",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TallyScoreTextField(
                text = state.score,
                onValueChange = { onInteraction(GameInteraction.ScoreChanged(it)) },
                onDone = { onInteraction(GameInteraction.DialogConfirmed) },
                label = "Score",
                placeHolder = "Your score...",
                keyboardType = KeyboardType.Number,
                maxChars = TallyScoreConfig.PLAYER_SCORE_MAX_CHARS,
                requestFocus = true,
                modifier = Modifier.padding(16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { onInteraction(GameInteraction.DialogConfirmed) },
                    enabled = state.isValid,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    TallyScoreText(
                        text = "Edit Score",
                        textStyle = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 8.dp, end = 8.dp)
                ) {
                    TallyScoreIconButton(
                        imageVector = Icons.Default.Delete,
                        onClick = { onInteraction(GameInteraction.DeleteScoreClicked(state.playerId, state.index)) },
                        modifier = Modifier.scale(0.9f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditScoreDialogPreview() {
    TallyScoreTheme {
        EditScoreDialog(state = DialogState.EditingScore(playerId = 1L, score = "42", index = 1))
    }
}