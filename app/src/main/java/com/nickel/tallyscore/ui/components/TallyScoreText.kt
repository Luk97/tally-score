package com.nickel.tallyscore.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun TallyScoreText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1
) {
    Text(
        text = text,
        style = textStyle,
        color = color,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Preview
@Composable
private fun TallyScoreTextPreview() {
    TallyScoreTheme {
        TallyScoreText("Hello, World")
    }
}