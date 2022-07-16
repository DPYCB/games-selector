package com.dpycb.gamesselector.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)) {
        val currentContext = LocalContext.current
        Text(
            text = "Переключить тему",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .clickable {
                    Toast
                        .makeText(
                            currentContext,
                            "Тут поменяется тема...",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
        )
        Text(
            text = "О создателях",
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .clickable(onClick = {
                    Toast
                        .makeText(
                            currentContext,
                            "Они лоу-скильные скотины",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                )
        )
    }
}