package com.nickel.tallyscore.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nickel.tallyscore.core.TallyScoreConfig
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.components.TallyScoreTextField
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.theme.TallyScoreTheme
import com.nickel.tallyscore.utils.InputValidator
import com.nickel.tallyscore.utils.handlePotentialMissingComma

@Composable
fun AddPlayerDialog(
    onInteraction: (GameInteraction) -> Unit = {},
) {
    var localName by remember { mutableStateOf("") }
    var localScore by remember { mutableStateOf("") }

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
            TallyScoreText(
                text = "Add Player",
                textStyle = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TallyScoreTextField(
                text = localName,
                onValueChange = { localName = it },
                onDone = {
                    if (InputValidator.isValidName(localName)) {
                        onInteraction(GameInteraction.AddPlayerConfirmed(localName, localScore))
                    }
                },
                label = "Name",
                placeHolder = "Your name...",
                maxChars = TallyScoreConfig.PLAYER_NAME_MAX_CHARS,
                requestFocus = true,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            TallyScoreTextField(
                text = localScore,
                onValueChange = { localScore = it.handlePotentialMissingComma() },
                onDone = {
                    if (InputValidator.isValidName(localName)) {
                        onInteraction(GameInteraction.AddPlayerConfirmed(localName, localScore))
                    }
                },
                label = "Score",
                placeHolder = "Your score...",
                keyboardType = KeyboardType.Number,
                maxChars = TallyScoreConfig.PLAYER_SCORE_MAX_CHARS,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = { onInteraction(GameInteraction.AddPlayerConfirmed(localName, localScore)) },
                enabled = InputValidator.isValidName(localName),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                TallyScoreText(
                    text = "Add Player",
                    textStyle = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddPlayerDialogPreview() {
    TallyScoreTheme {
        AddPlayerDialog()
    }
}