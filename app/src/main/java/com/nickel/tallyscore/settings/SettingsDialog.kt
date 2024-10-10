package com.nickel.tallyscore.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
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
import com.nickel.tallyscore.design.TallyScoreTheme
import com.nickel.tallyscore.design.components.TallyScoreText
import com.nickel.tallyscore.persistence.preferences.AppTheme
import com.nickel.tallyscore.persistence.preferences.UserPreferences
import com.nickel.tallyscore.settings.SettingsViewModel.SettingsState

@Composable
internal fun SettingsDialog(
    viewModel: SettingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    val state by viewModel.settingsState.collectAsStateWithLifecycle()

    SettingsDialog(
        state = state,
        onDismiss = onDismiss,
        onZoomLevelChanged = viewModel::onZoomLevelChanged,
        onAppThemeChanged = viewModel::onAppThemeChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDialog(
    state: SettingsState,
    onDismiss: () -> Unit = {},
    onZoomLevelChanged: (Float) -> Unit = {},
    onAppThemeChanged: (AppTheme) -> Unit = {}
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
                        onZoomLevelChanged = onZoomLevelChanged,
                        onAppThemeChanged = onAppThemeChanged
                    )
                }
            }

        }
    }
}

@Composable
private fun SettingsPanel(
    settings: UserPreferences,
    onZoomLevelChanged: (Float) -> Unit = {},
    onAppThemeChanged: (AppTheme) -> Unit = {}
) {
    DialogSectionTitle(stringResource(R.string.board_size))
    Column(modifier = Modifier.selectableGroup()) {
        BoardSizeSlider(
            zoomLevel = settings.zoomLevel,
            onZoomLevelChanged = onZoomLevelChanged
        )
    }
    HorizontalDivider(
        color = TallyScoreTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
    AppThemeOptionsRow(
        appTheme = settings.appTheme,
        onAppThemeChanged = onAppThemeChanged
    )
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

@Composable
private fun AppThemeOptionsRow(
    appTheme: AppTheme,
    onAppThemeChanged: (AppTheme) -> Unit = {}
) {
    Row {
        AppThemeOption(
            label = "Light",
            selected = appTheme == AppTheme.LIGHT,
            onClick = { onAppThemeChanged(AppTheme.LIGHT) }
        )
        AppThemeOption(
            label = "Dark",
            selected = appTheme == AppTheme.DARK,
            onClick = { onAppThemeChanged(AppTheme.DARK) }
        )
        AppThemeOption(
            label = "System",
            selected = appTheme == AppTheme.SYSTEM,
            onClick = { onAppThemeChanged(AppTheme.SYSTEM) }
        )
    }
}

@Composable
fun AppThemeOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    Column {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        TallyScoreText(label)
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