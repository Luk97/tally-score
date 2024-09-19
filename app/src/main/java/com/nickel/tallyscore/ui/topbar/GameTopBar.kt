package com.nickel.tallyscore.ui.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun GameTopBar(viewModel: GameTopBarViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    TopBarContent(
        state = state,
        onInteraction = viewModel::onInteraction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarContent(
    state: GameTopBarState,
    modifier: Modifier = Modifier,
    onInteraction: (GameTopBarInteraction) -> Unit = {}
) {
    TopAppBar(
        title = {
            Text("Tally Score")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            TallyScoreIconButton(
                imageVector = Icons.Default.Menu,
                onClick = { onInteraction(GameTopBarInteraction.MenuClicked) }
            )
            MenuDropDown(
                state = state,
                onInteraction = onInteraction
            )
        },
        modifier = modifier
    )


}

@Composable
private fun MenuDropDown(
    state: GameTopBarState,
    onInteraction: (GameTopBarInteraction) -> Unit = {}
) {
    DropdownMenu(
        expanded = state.showMenu,
        onDismissRequest = { onInteraction(GameTopBarInteraction.MenuDismissed) }
    ) {
        DropdownMenuItem(
            text = { Text("Option 1") },
            onClick = {}
        )
        DropdownMenuItem(
            text = { Text("Option 2") },
            onClick = {}
        )
        DropdownMenuItem(
            text = { Text("Option 3") },
            onClick = {}
        )
        DropdownMenuItem(
            text = { Text("Option 4") },
            onClick = {}
        )
    }
}


@Preview
@Composable
private fun GameTopBarPreview() {
    TallyScoreTheme {
        TopBarContent(state = GameTopBarState())
    }
}