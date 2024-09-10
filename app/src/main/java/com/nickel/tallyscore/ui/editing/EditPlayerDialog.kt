package com.nickel.tallyscore.ui.editing

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.tallyscore.ui.components.TallyScoreTextField
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun EditPlayerDialog(
    viewModel: EditPlayerViewModel = viewModel(),
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    EditPlayerDialog(
        state = state,
        onInteraction = viewModel::onInteraction,
        onDismiss = onDismiss
    )
}

@Composable
private fun EditPlayerDialog(
    state: EditState,
    onInteraction: (EditInteraction) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Dialog(onDismissRequest = onDismiss) {
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
                text = "Add Player",
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            TallyScoreTextField(
                value = state.name,
                onValueChange = { onInteraction(EditInteraction.NameChanged(it)) },
                label = "Name",
                placeHolder = "Your name...",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .focusRequester(focusRequester)
            )
            TallyScoreTextField(
                value = state.score,
                onValueChange = { onInteraction(EditInteraction.ScoreChanged(it)) },
                label = "Score",
                placeHolder = "Your score...",
                keyboardType = KeyboardType.Number,
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick = {
                    onInteraction(EditInteraction.AddPlayerClicked)
                    onDismiss()
                },
                enabled = state.validPlayer,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Create Player")
            }
        }
    }
}

@Preview
@Composable
private fun AddPlayerDialogPreview() {
    TallyScoreTheme {
        EditPlayerDialog(state = EditState(name = "Lukas"))
    }
}