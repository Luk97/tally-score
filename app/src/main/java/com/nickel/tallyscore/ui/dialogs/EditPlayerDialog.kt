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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nickel.tallyscore.R
import com.nickel.tallyscore.design.TallyScoreTheme
import com.nickel.tallyscore.core.TallyScoreConfig
import com.nickel.tallyscore.persistence.player.Player
import com.nickel.tallyscore.design.components.TallyScoreIconButton
import com.nickel.tallyscore.design.components.TallyScoreText
import com.nickel.tallyscore.design.components.TallyScoreTextField
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.utils.InputValidator

@Composable
internal fun EditPlayerDialog(
    player: Player,
    onInteraction: (GameInteraction) -> Unit = {},
) {
    var localName by remember { mutableStateOf(player.name) }

    Dialog(onDismissRequest = { onInteraction(GameInteraction.DialogDismissed) }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(TallyScoreTheme.colorScheme.surfaceContainer)
        ) {
            TallyScoreText(
                text = stringResource(R.string.edit_player),
                textStyle = TallyScoreTheme.typography.titleLarge,
                color = TallyScoreTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TallyScoreTextField(
                text = localName,
                onValueChange = { localName = it },
                onDone = {
                    if (InputValidator.isValidName(localName)) {
                        onInteraction(GameInteraction.EditPlayerConfirmed(player, localName))
                    }
                },
                label = stringResource(R.string.name),
                placeHolder = stringResource(R.string.enter_name),
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
                    onClick = { onInteraction(GameInteraction.EditPlayerConfirmed(player, localName)) },
                    enabled = InputValidator.isValidName(localName),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TallyScoreTheme.colorScheme.primary,
                        contentColor = TallyScoreTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    TallyScoreText(
                        text = stringResource(R.string.edit_player),
                        textStyle = TallyScoreTheme.typography.labelLarge,
                        color = TallyScoreTheme.colorScheme.onPrimary
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
                        onClick = { onInteraction(GameInteraction.DeletePlayerClicked(player)) },
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
        EditPlayerDialog(player = Player("Lukas"))
    }
}