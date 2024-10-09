package com.nickel.tallyscore.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.nickel.tallyscore.design.TallyScoreTheme

@Composable
fun TallyScoreIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconColor: Color = TallyScoreTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .clip(CircleShape)
            .background(TallyScoreTheme.colorScheme.primary)
            .then(modifier)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.fillMaxSize(0.9f)
        )
    }
}

@Preview
@Composable
private fun TallyScoreIconButtonPreview() {
    TallyScoreTheme {
        TallyScoreIconButton(
            imageVector = Icons.Default.Delete
        )
    }
}