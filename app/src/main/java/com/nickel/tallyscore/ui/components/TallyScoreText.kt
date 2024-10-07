package com.nickel.tallyscore.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.nickel.tallyscore.preferences.UserPreferences.BoardSize
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun TallyScoreText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TallyScoreTheme.typography.bodyLarge,
    color: Color = TallyScoreTheme.colorScheme.onBackground,
    maxLines: Int = 1,
    boardSize: BoardSize? = null,
    fontWeight: FontWeight? = null
) {
    Text(
        text = text,
        style = textStyle,
        color = color,
        maxLines = maxLines,
        modifier = modifier,
        fontSize = when (boardSize) {
            BoardSize.SMALL -> TallyScoreTheme.typography.bodySmall.fontSize
            BoardSize.MEDIUM -> TallyScoreTheme.typography.bodyMedium.fontSize
            BoardSize.LARGE -> TallyScoreTheme.typography.bodyLarge.fontSize
            else -> TextUnit.Unspecified
        },
        fontWeight = fontWeight,
        overflow = TextOverflow.Clip
    )
}

@Preview
@Composable
private fun TallyScoreTextPreview() {
    TallyScoreTheme {
        TallyScoreText("Hello, World")
    }
}