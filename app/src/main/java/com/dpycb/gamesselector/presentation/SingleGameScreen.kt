package com.dpycb.gamesselector.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.dpycb.gamesselector.presentation.games.GameDetailViewState
import com.dpycb.gamesselector.presentation.games.SingleGameViewModel
import com.dpycb.gamesselector.presentation.uikit.MyComposeColors
import com.dpycb.gamesselector.presentation.uikit.MyComposeTheme

@Composable
fun SingleGameScreen(gameId: Long) {
    val viewModel: SingleGameViewModel = hiltViewModel()
    viewModel.updateGameDetailViewState(gameId)
    val viewState = viewModel.getGameDetailViewStateFlow().collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewState.value.id == 0L) {
            CircularProgressIndicator()
        } else {
            SubcomposeAsyncImage(
                model = viewState.value.coverRef,
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .background(color = MaterialTheme.colors.surface)
                ,
                verticalArrangement = Arrangement.Top
            ) {
                GameMediaListView(
                    modifier = Modifier.padding(12.dp),
                    mediaList = viewState.value.screenshotsRef
                )
                GameItemDescriptionView(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    title = "Описание",
                    game = viewState.value
                )
                GameRatingView(
                    modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    rating = viewState.value.rating,
                    ratingCount = viewState.value.ratingCount
                )
            }
        }
    }
}

@Composable
fun GameMediaListView(
    modifier: Modifier = Modifier,
    mediaList: List<String>,
) {
    LazyRow(
        modifier = modifier
    ) {
        items(mediaList) { mediaRef ->
            SubcomposeAsyncImage(
                model = mediaRef,
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(120.dp)
                    .width(100.dp)
            )
        }
    }
}

@Composable
fun GameItemDescriptionView(modifier: Modifier, title: String, game: GameDetailViewState) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold,
        )
        val textViews = listOf(
            "Жанр: ${game.genre}" to Modifier.padding(top = 12.dp),
            "Дата выхода: ${game.releaseDate}" to Modifier,
            "Платформы: ${game.platforms}" to Modifier.padding(top = 16.dp),
            game.description to Modifier.padding(top = 16.dp),
        )
        textViews.forEachIndexed { index, (text, modifier) ->
            val maxLines = if (textViews.lastIndex == index) Int.MAX_VALUE else 2
            GameInfoSubtitles(text, modifier, maxLines)
        }
    }
}

@Composable
fun GameRatingView(modifier: Modifier, rating: Double, ratingCount: Int) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Оценки",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold,
        )
        //TODO add circular view?
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Общий рейтинг: $rating",
                color = getColorForRating(rating),
                style = MaterialTheme.typography.h4
            )
        }
    }
}

@Composable
fun getColorForRating(rating: Double): Color {
    return when {
        rating >= 80 -> MyComposeTheme.colors.ratingBest
        rating >= 60 -> MyComposeTheme.colors.ratingGood
        rating >= 40 -> MyComposeTheme.colors.ratingNormal
        else -> MyComposeTheme.colors.ratingBad
    }
}