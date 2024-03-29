package com.dpycb.gamesselector.presentation.gameslist

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.dpycb.gamesselector.presentation.singlegame.GameDetailViewState

@Composable
fun GamesListScreen(
    onGameClicked: (Long) -> Unit = {}
) {
    val viewModel: GamesListViewModel = hiltViewModel()
    val newGamesViewState = viewModel.getNewGamesListFlow().collectAsState()
    val mainPosterViewState = viewModel.getMainPosterGameFlow().collectAsState()
    if (newGamesViewState.value.gamesListItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp))
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainPoster(
                viewState = mainPosterViewState,
                modifier = Modifier.padding(top = 12.dp),
                onGameClicked = onGameClicked
            )

            GamesPreviewList(
                games = newGamesViewState.value.gamesListItems,
                onGameClicked = onGameClicked,
                onCategoryExpand = viewModel::onCategoryClick,
                titleText = "Новинки",
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
fun MainPoster(
    viewState: State<GameDetailViewState>,
    modifier: Modifier = Modifier,
    onGameClicked: (Long) -> Unit,
) {
    val game = viewState.value
    if (game.id == 0L) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
            )
        }
    }
    else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
                .clickable { onGameClicked(game.id) },
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start),
            verticalAlignment = Alignment.Top
        ) {
            SubcomposeAsyncImage(
                model = game.coverRef,
                contentScale = ContentScale.Crop,
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .width(120.dp)
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
                ).forEach { (text, modifier) ->
                    GameInfoSubtitles(text, modifier)
                }

                MediaSmallPreviewListView(
                    modifier = Modifier.padding(top = 12.dp),
                    mediaList = game.screenshotsRef
                )
            }
        }
    }
}

@Composable
fun MediaSmallPreviewListView(modifier: Modifier = Modifier, mediaList: List<String>) {
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
                    .height(45.dp)
                    .width(70.dp)
            )
        }
    }
}


@Composable
fun GamesPreviewList(
    games: List<GameListItemViewState>,
    onGameClicked: (Long) -> Unit,
    onCategoryExpand: (Context) -> Unit = { },
    titleText: String,
    modifier: Modifier = Modifier,
    hasCategoryButton: Boolean = true,
    titleTextStyle: TextStyle = MaterialTheme.typography.body2,
    titleTextWeight: FontWeight? = null
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
                style = titleTextStyle,
                fontWeight = titleTextWeight,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp)
            )
            if (hasCategoryButton) {
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
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            items(games.take(10)) { game ->
                GameListItemView(game = game, onGameClicked = onGameClicked)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameListItemView(game: GameListItemViewState, onGameClicked: (Long) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .width(90.dp)
            .padding(horizontal = 8.dp)
            .clickable { onGameClicked(game.id) }
    ) {
        SubcomposeAsyncImage(
            model = game.imageRef,
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier.height(120.dp)
        )
        Text(
            text = game.name,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.caption,
        )
        Text(
            text = game.genre,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.overline,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
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
    GamesListScreen()
}