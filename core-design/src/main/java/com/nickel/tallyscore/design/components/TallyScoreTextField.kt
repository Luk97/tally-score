package com.nickel.tallyscore.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.nickel.tallyscore.design.TallyScoreTheme

@Composable
fun TallyScoreTextField(
    text: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    onDone: () -> Unit = {},
    label: String? = null,
    placeHolder: String? = null,
    maxChars: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    requestFocus: Boolean = false
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = text)) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (requestFocus) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(text) {
        textFieldValue = TextFieldValue(text = text, selection = TextRange(text.length))
    }

    TextField(
        modifier = modifier.focusRequester(focusRequester),
        onValueChange = {
            if (maxChars == null || it.text.length <= maxChars) {
                onValueChange(it.text)
            }
        },
        value = textFieldValue,
        label = label?.let { { Text(it) } },
        placeholder = placeHolder?.let { { Text(
            text = it,
            color = TallyScoreTheme.colorScheme.onSurfaceVariant
        ) } },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onDone() }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = TallyScoreTheme.colorScheme.surfaceContainer,
            unfocusedContainerColor = TallyScoreTheme.colorScheme.surfaceContainer,
            focusedTextColor = TallyScoreTheme.colorScheme.onSurface,
            unfocusedTextColor = TallyScoreTheme.colorScheme.onSurfaceVariant,
            focusedIndicatorColor = TallyScoreTheme.colorScheme.primary,
            unfocusedIndicatorColor = TallyScoreTheme.colorScheme.onSurfaceVariant,
            focusedLabelColor = TallyScoreTheme.colorScheme.primary,
            unfocusedLabelColor = TallyScoreTheme.colorScheme.onSurfaceVariant
        ),
        supportingText = maxChars?.let {
            {
                Text(
                    text = "${text.length} / $maxChars",
                    color = TallyScoreTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )
            }
        }
    )
}

@Preview
@Composable
private fun TallyScoreTextFieldPreview() {
    TallyScoreTheme {
        TallyScoreTextField(
            text = "Lukas",
            label = "Name",
            placeHolder = "Your name...",
            maxChars = 6
        )
    }
}