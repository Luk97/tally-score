package com.nickel.tallyscore.design.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nickel.tallyscore.design.TallyScoreTheme

@Composable
fun AddPlayerButton(
    modifier: Modifier = Modifier,
    onAddPlayerClicked: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onAddPlayerClicked,
        containerColor = TallyScoreTheme.colorScheme.primary,
        contentColor = TallyScoreTheme.colorScheme.onPrimary,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize)
        )
    }
}

@Preview
@Composable
private fun AddPlayerButtonPreview() {
    TallyScoreTheme {
        AddPlayerButton()
    }
}