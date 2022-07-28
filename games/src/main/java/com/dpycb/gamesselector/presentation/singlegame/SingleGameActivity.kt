package com.dpycb.gamesselector.presentation.singlegame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.dpycb.gamesselector.uikit.MyComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleGameActivity : AppCompatActivity() {
    companion object {
        private const val GAME_ID = "GameId"
        fun create(context: Context, gameId: Long) {
            val intent = Intent(context, SingleGameActivity::class.java).apply {
                putExtra(GAME_ID, gameId)
            }
            context.startActivity(intent)
        }
    }
    private val gameId by lazy {
        intent?.getLongExtra(GAME_ID, 0L) ?: 0L
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                SingleGameScreen(gameId)
            }
        }
    }
}