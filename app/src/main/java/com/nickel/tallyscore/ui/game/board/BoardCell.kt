package com.nickel.tallyscore.ui.game.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.data.Player
import com.nickel.tallyscore.ui.components.TallyScoreIconButton
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.game.GameInteraction

@Composable
fun StandardBoardCell(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    ) {
        TallyScoreText(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun AddScoreBoardCell(
    player: Player,
    modifier: Modifier = Modifier,
    onInteraction: (GameInteraction) -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    ) {
        TallyScoreIconButton(
            imageVector = Icons.Default.Add,
            onClick = { onInteraction(GameInteraction.AddScoreClicked(player)) },
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}