package com.dpycb.gamesselector.presentation.uikit

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val lightMaterial = lightColors(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    background = BackgroundLight,
)

//TODO add dark colors
private val darkMaterial = darkColors()

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