package com.nickel.tallyscore.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nickel.tallyscore.ui.theme.TallyScoreTheme

@Composable
fun TallyScoreText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TallyScoreTheme.typography.bodyLarge,
    color: Color = TallyScoreTheme.colorScheme.onBackground,
    maxLines: Int = 1,
    fontWeight: FontWeight? = null
) {
    var fontSize by remember(text) { mutableStateOf(14.sp) }
    Text(
        text = text,
        style = textStyle,
        color = color,
        maxLines = maxLines,
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        overflow = TextOverflow.Clip,
        onTextLayout = { result ->
            if (result.didOverflowWidth || result.didOverflowHeight) {
                fontSize *= 0.9
            }
        }
    )
}

@Preview
@Composable
private fun TallyScoreTextPreview() {
    TallyScoreTheme {
        TallyScoreText("Hello, World")
    }
}