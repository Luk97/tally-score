package com.nickel.tallyscore.ui.dialogs.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nickel.tallyscore.R
import com.nickel.tallyscore.preferences.UserPreferences
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.dialogs.settings.SettingsViewModel.SettingsState
import com.nickel.tallyscore.TallyScoreTheme

@Composable
internal fun SettingsDialog(
    viewModel: SettingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()

    SettingsDialog(
        state = state,
        onDismiss = onDismiss,
        onZoomLevelChanged = viewModel::onZoomLevelChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDialog(
    state: SettingsState,
    onDismiss: () -> Unit = {},
    onZoomLevelChanged: (Float) -> Unit = {}
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
            color = TallyScoreTheme.colorScheme.surface,
            contentColor = TallyScoreTheme.colorScheme.onSurface
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TallyScoreText(
                    text = stringResource(R.string.settings),
                    textStyle = TallyScoreTheme.typography.titleLarge
                )
                HorizontalDivider(
                    color = TallyScoreTheme.colorScheme.onSurface
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
                        onZoomLevelChanged = onZoomLevelChanged
                    )
                }
            }

        }
    }
}

@Composable
private fun SettingsPanel(
    settings: UserPreferences,
    onZoomLevelChanged: (Float) -> Unit
) {
    DialogSectionTitle(stringResource(R.string.board_size))
    Column(modifier = Modifier.selectableGroup()) {
        BoardSizeSlider(
            zoomLevel = settings.zoomLevel,
            onZoomLevelChanged = onZoomLevelChanged
        )
    }
}

@Composable
private fun DialogSectionTitle(text: String) {
    TallyScoreText(
        text = text,
        textStyle = TallyScoreTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

@Composable
private fun BoardSizeSlider(
    zoomLevel: Float,
    onZoomLevelChanged: (Float) -> Unit = {}
) {
    Column {
        Slider(
            value = zoomLevel,
            onValueChange = {
                onZoomLevelChanged(it)
            },
            colors = SliderDefaults.colors(
                thumbColor = TallyScoreTheme.colorScheme.primary,
                activeTrackColor = TallyScoreTheme.colorScheme.primary,
                inactiveTrackColor = TallyScoreTheme.colorScheme.primary,
            ),
            valueRange = 0.5f..2f
        )
        TallyScoreText(text = zoomLevel.toString())
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