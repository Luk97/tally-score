package com.nickel.tallyscore.ui.dialogs.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickel.tallyscore.R
import com.nickel.tallyscore.preferences.UserPreferences
import com.nickel.tallyscore.preferences.UserPreferences.BoardSize
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.dialogs.settings.SettingsViewModel.SettingsState
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun SettingsDialog(
    viewModel: SettingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()

    SettingsDialog(
        state = state,
        onDismiss = onDismiss,
        onBoardSizeClicked = viewModel::onBoardSizeClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDialog(
    state: SettingsState,
    onDismiss: () -> Unit = {},
    onBoardSizeClicked: (BoardSize) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    BasicAlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismiss,
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp)
    ) {
        Surface(
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shape = AlertDialogDefaults.shape,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TallyScoreText(
                    text = stringResource(R.string.settings),
                    textStyle = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface
                )
                when (state) {
                    is SettingsState.Loading -> {
                        TallyScoreText(
                            text = stringResource(R.string.loading),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    is SettingsState.Success -> SettingsPanel(
                        settings = state.settings,
                        onBoardSizeClicked = onBoardSizeClicked
                    )
                }
            }

        }
    }
}

@Composable
private fun SettingsPanel(
    settings: UserPreferences,
    onBoardSizeClicked: (BoardSize) -> Unit = {}
) {
    DialogSectionTitle(stringResource(R.string.board_size))
    Column(modifier = Modifier.selectableGroup()) {
        BoardSizeChooserRow(
            boardSize = settings.boardSize,
            onBoardSizeClicked = onBoardSizeClicked
        )
    }
}

@Composable
private fun DialogSectionTitle(text: String) {
    TallyScoreText(
        text = text,
        textStyle = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
private fun BoardSizeChooserRow(
    boardSize: BoardSize,
    onBoardSizeClicked: (BoardSize) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        BoardSizeChooserOption(
            text = stringResource(R.string.small),
            selected = boardSize == BoardSize.SMALL,
            onClick = { onBoardSizeClicked(BoardSize.SMALL) }
        )
        BoardSizeChooserOption(
            text = stringResource(R.string.medium),
            selected = boardSize == BoardSize.MEDIUM,
            onClick = { onBoardSizeClicked(BoardSize.MEDIUM) }
        )
        BoardSizeChooserOption(
            text = stringResource(R.string.large),
            selected = boardSize == BoardSize.LARGE,
            onClick = { onBoardSizeClicked(BoardSize.LARGE) }
        )

    }
}

@Composable
private fun RowScope.BoardSizeChooserOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f)
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick
            )
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        TallyScoreText(
            text = text,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsDialogSuccess() {
    TallyScoreTheme {
        SettingsDialog(
            state = SettingsState.Success(
                settings = UserPreferences()
            )
        )
    }
}

@Preview
@Composable
private fun PreviewSettingsDialogLoading() {
    TallyScoreTheme {
        SettingsDialog(state = SettingsState.Loading)
    }
}