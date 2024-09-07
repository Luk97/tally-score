package com.nickel.tallyscore.ui.editing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column {
            TallyScoreTextField(
                value = state.name,
                onValueChange = { onInteraction(EditInteraction.NameChanged(it)) },
                label = "Name",
                placeHolder = "Your name..."
            )
            TallyScoreTextField(
                value = "${state.score}",
                onValueChange = { onInteraction(EditInteraction.ScoreChanged(it)) },
                label = "Score",
                placeHolder = "Your score...",
                keyboardType = KeyboardType.Number
            )
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