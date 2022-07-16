package com.dpycb.gamesselector.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.dpycb.gamesselector.presentation.navigation.BottomNavigationView
import com.dpycb.gamesselector.presentation.navigation.NavGraph
import com.dpycb.gamesselector.presentation.navigation.TopActionBar
import com.dpycb.gamesselector.presentation.uikit.MyComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopActionBar() },
                    bottomBar = { BottomNavigationView(navController) }
                ) {
                    NavGraph(navController)
                }
            }
        }
    }
}