package com.dpycb.gamesselector.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.dpycb.gamesselector.presentation.uikit.PrimaryDark
import com.dpycb.gamesselector.presentation.uikit.PrimaryLight

private val lightMaterial = lightColors(
    primary = PrimaryLight
)

private val darkMaterial = darkColors(
    primary = PrimaryDark
)

@Composable
fun MyComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val materialColors = when (darkTheme) {
        true -> darkMaterial
        else -> lightMaterial
    }
    MaterialTheme(
        colors = materialColors,
        content = content
    )
}