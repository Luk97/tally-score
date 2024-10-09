package com.nickel.tallyscore.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.nickel.tallyscore.ui.game.GameScreen
import com.nickel.tallyscore.design.TallyScoreTheme
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
                    color = TallyScoreTheme.colorScheme.primary,
                    darkIcons = isSystemInDarkTheme()
                )
                systemUiController.setNavigationBarColor(
                    color = TallyScoreTheme.colorScheme.surfaceContainer,
                    darkIcons = false
                )
                Surface(
                    color = TallyScoreTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {
                    GameScreen()
                }
            }
        }
    }
}

