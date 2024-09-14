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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nickel.tallyscore.core.TallyScoreConfig
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.components.TallyScoreTextField
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.game.GameState.DialogState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun EditPlayerDialog(
    state: DialogState.EditingPlayer,
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
                text = "Edit Player",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TallyScoreTextField(
                text = state.player.name,
                onValueChange = { onInteraction(GameInteraction.NameChanged(it)) },
                onDone = { onInteraction(GameInteraction.DialogConfirmed) },
                label = "Player",
                placeHolder = "Your name...",
                maxChars = TallyScoreConfig.PLAYER_NAME_MAX_CHARS,
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
                    Text("Edit Player")
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
                        onClick = { onInteraction(GameInteraction.DeletePlayerClicked(state.player)) },
                        modifier = Modifier.scale(0.9f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditPlayerDialogPreview() {
    TallyScoreTheme {
        EditPlayerDialog(state = DialogState.EditingPlayer(Player(name = "Lukas")))
    }
}