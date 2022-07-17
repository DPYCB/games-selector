package com.dpycb.gamesselector.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Column(Modifier.fillMaxSize()) {
        val currentContext = LocalContext.current
        SimpleListItemView(
            text = "Переключить тему",
            onClick = {
                Toast
                    .makeText(
                        currentContext,
                        "Тут поменяется тема...",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        )
        SimpleListItemView(
            text = "О создателях",
            onClick = {
                Toast
                    .makeText(
                        currentContext,
                        "Они лоу-скильные скотины",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        )
    }
}

@Composable
fun SimpleListItemView(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
    }
}