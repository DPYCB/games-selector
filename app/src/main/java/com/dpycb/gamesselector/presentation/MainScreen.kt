package com.dpycb.gamesselector.presentation

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.dpycb.gamesselector.presentation.games.GameListItemViewState
import com.dpycb.gamesselector.presentation.games.GamesListViewModel

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val viewModel: GamesListViewModel = hiltViewModel()
        val newGamesViewState = viewModel.getNewGamesListFlow().collectAsState()

        if (newGamesViewState.value.gamesListItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp))
            }
        }
        else {
            MainPoster(
                game = newGamesViewState.value.gamesListItems.randomOrNull() ?: GameListItemViewState(),
                modifier = Modifier.padding(top = 12.dp)
            )

            GamesPreviewList(
                games = newGamesViewState.value.gamesListItems,
                onMovieClicked = viewModel::onMovieClicked,
                onCategoryExpand = viewModel::onCategoryClick,
                titleText = "Новинки",
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun MainPoster(game: GameListItemViewState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color = MaterialTheme.colors.surface),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
        verticalAlignment = Alignment.Top
    ) {
        SubcomposeAsyncImage(
            model = game.imageRef,
            contentScale = ContentScale.Crop,
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier.fillMaxHeight().width(120.dp)
        )

        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(end = 14.dp)
        ) {
            Text(
                text = game.name,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 12.dp)
            )
            listOf(
                "Жанр: ${game.genre}" to Modifier.padding(top = 12.dp),
                "Дата выхода: ${game.releaseDate}" to Modifier,
                "Платформы: ${game.platforms}" to Modifier.padding(top = 16.dp),
                game.description to Modifier.padding(top = 16.dp),
                ).forEach{ (text, modifier) ->
                GameInfoSubtitles(text, modifier)
            }
        }
    }
}

@Composable
fun GamesPreviewList(
    games: List<GameListItemViewState>,
    onMovieClicked: (Context, Long) -> Unit,
    onCategoryExpand: (Context) -> Unit,
    titleText: String,
    modifier: Modifier = Modifier
) {
    val currentContext = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp)
            )
            Button(
                onClick = { onCategoryExpand(currentContext) },
                colors = ButtonDefaults.textButtonColors(),
                modifier = Modifier.align(Alignment.CenterVertically),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = "все",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary,
                )
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(games.take(10)) { game ->
                GameListItemView(game = game, onMovieClicked = onMovieClicked)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameListItemView(game: GameListItemViewState, onMovieClicked: (Context, Long) -> Unit) {
    val currentContext = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .size(width = 120.dp, height = 160.dp)
            .clickable(onClick = { onMovieClicked(currentContext, game.id) })
    ) {
        val (image, title, subtitle) = createRefs()
        SubcomposeAsyncImage(
            model = game.imageRef,
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier
                .size(width = 90.dp, height = 120.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(
            text = game.name,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom)
                start.linkTo(image.start)
                end.linkTo(image.end)
            }
        )
        Text(
            text = game.genre,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.overline,
            textAlign = TextAlign.Start,
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(title.start)
            }
        )
    }
}

@Composable
fun GameInfoSubtitles(text: String, modifier: Modifier = Modifier, maxLines: Int = 2) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSurface,
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}