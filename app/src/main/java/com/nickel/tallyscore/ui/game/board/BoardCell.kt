package com.nickel.tallyscore.ui.game.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.player.Player
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.game.GameInteraction
import com.nickel.tallyscore.ui.theme.TallyScoreTheme
import com.nickel.tallyscore.ui.theme.localcompositionprovider.LocalDimensions

@Composable
internal fun TextBoardCell(
    text: String,
    zoomLevel: Float,
    modifier: Modifier = Modifier
) {
    BoardCellContainer(zoomLevel, modifier) {
        TallyScoreText(
            text = text,
            zoomLevel = zoomLevel,
            color = TallyScoreTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
internal fun EmptyBoardCell(zoomLevel: Float) {
    BoardCellContainer(zoomLevel) {}
}

@Composable
internal fun AddScoreBoardCell(
    player: Player,
    zoomLevel: Float,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    BoardCellContainer(zoomLevel, modifier) {
        TallyScoreIconButton(
            imageVector = Icons.Default.Add,
            onClick = { onInteraction(GameInteraction.AddScoreClicked(player)) },
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .aspectRatio(1f)
        )
    }
}

@Composable
private fun BoardCellContainer(
    zoomLevel: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val dimensions = LocalDimensions.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(
                width = dimensions.cellWidth * zoomLevel,
                height = dimensions.cellHeight * zoomLevel
            )
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    ) {
        content()
    }
}