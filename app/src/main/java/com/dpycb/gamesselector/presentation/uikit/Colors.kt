package com.dpycb.gamesselector.presentation.uikit

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class MyComposeColors(
    val ratingBest: Color,
    val ratingGood: Color,
    val ratingNormal: Color,
    val ratingBad: Color,
)

val PrimaryLight = Color(0xFF096082)
val SecondaryLight = Color(0xFFDC08FF)
val BackgroundLight = Color(0xFFF3F3F3)

val RatingBestLight = Color(0xFF00C508)
val RatingGoodLight = Color(0xFFEEE510)
val RatingNormalLight = Color(0xFFEEB010)
val RatingBadLight = Color(0xFFD77D31)


//TODO add colors for night theme
val PrimaryDark = Color(0xFFD77D31)
val SecondaryDark = Color(0xFF9A1BAF)

val RatingBestDark = Color(0xFF00C508)
val RatingGoodDark = Color(0xFFEEE510)
val RatingNormalDark = Color(0xFFEEB010)
val RatingBadDark = Color(0xFFD77D31)