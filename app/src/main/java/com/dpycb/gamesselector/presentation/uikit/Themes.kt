package com.dpycb.gamesselector.presentation.uikit

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalExtendedColors = staticCompositionLocalOf {
    MyComposeColors(
        ratingBest = Color.Unspecified,
        ratingGood = Color.Unspecified,
        ratingNormal = Color.Unspecified,
        ratingBad = Color.Unspecified,
    )
}

private val lightMaterial = lightColors(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    background = BackgroundLight,
)

//TODO add dark colors
private val darkMaterial = darkColors()

private val lightPalette = MyComposeColors(
    ratingBest = RatingBestLight,
    ratingGood = RatingGoodLight,
    ratingNormal = RatingNormalLight,
    ratingBad = RatingBadLight,
)

private val darkPalette = MyComposeColors(
    ratingBest = RatingBestDark,
    ratingGood = RatingGoodDark,
    ratingNormal = RatingNormalDark,
    ratingBad = RatingBadDark,
)

@Composable
fun MyComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val (materialColors, myColors) = when (darkTheme) {
        true -> darkMaterial to darkPalette
        else -> lightMaterial to lightPalette
    }
    CompositionLocalProvider(LocalExtendedColors provides myColors) {
        MaterialTheme(
            colors = materialColors,
            content = content
        )
    }
}

object MyComposeTheme {
    val colors: MyComposeColors
        @Composable
        get() = LocalExtendedColors.current
}