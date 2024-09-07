package com.nickel.tallyscore.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
               GameScreen()
            }
        }
    }
}