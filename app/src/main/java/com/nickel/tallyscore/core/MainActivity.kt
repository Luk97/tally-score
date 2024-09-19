package com.nickel.tallyscore.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nickel.tallyscore.ui.game.GameScreen
import com.nickel.tallyscore.ui.theme.TallyScoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TallyScoreTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    color = MaterialTheme.colorScheme.primary,
                    darkIcons = true
                )
                systemUiController.setNavigationBarColor(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    darkIcons = false
                )
                Box(modifier = Modifier.systemBarsPadding()) {
                    GameScreen()
                }
            }
        }
    }
}