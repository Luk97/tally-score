package com.nickel.tallyscore.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun TallyScoreTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    label: String? = null,
    placeHolder: String? = null,
    keyboardType: KeyboardType = KeyboardType.Unspecified
) {
    TextField(
        modifier = modifier,
        onValueChange = onValueChange,
        value = value,
        label = label?.let {{ Text(it) }},
        placeholder = placeHolder?.let {{ Text(it) }},
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}

@Preview
@Composable
private fun TallyScoreTextFieldPreview() {
    TallyScoreTheme {
        TallyScoreTextField(
            value = "Lukas",
            label = "Name",
            placeHolder = "Your name..."
        )
    }
}