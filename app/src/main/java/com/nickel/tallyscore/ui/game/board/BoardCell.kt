package com.nickel.tallyscore.ui.game.board

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nickel.tallyscore.player.Player
import com.nickel.tallyscore.preferences.UserPreferences.BoardSize
import com.nickel.tallyscore.ui.components.TallyScoreText
import com.nickel.tallyscore.ui.game.GameInteraction

@Composable
fun StandardBoardCell(
    text: String,
    boardSize: BoardSize,
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
            color = MaterialTheme.colorScheme.onBackground,
            boardSize = boardSize
        )
    }
}

@Composable
fun PlayerNameBoardCell(
    text: String,
    boardSize: BoardSize,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    ) {
        TallyScoreText(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            boardSize = boardSize
        )
        imageRes?.let {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .height(
                        when (boardSize) {
                            BoardSize.SMALL -> 24.dp
                            BoardSize.MEDIUM -> 32.dp
                            BoardSize.LARGE -> 48.dp
                        }
                    )
            )
        }
    }
}

@Composable
fun StatsBoardCell(
    text: String,
    boardSize: BoardSize,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .then(modifier)
    ) {
        TallyScoreText(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            boardSize = boardSize,
            fontWeight = FontWeight.ExtraBold
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
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxHeight(0.75f)
                .aspectRatio(1f)
                .clickable { onInteraction(GameInteraction.AddScoreClicked(player)) }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}