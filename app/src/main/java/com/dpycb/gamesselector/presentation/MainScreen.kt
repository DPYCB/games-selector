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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.dpycb.gamesselector.R
import com.dpycb.gamesselector.presentation.games.GamesListViewModel
import proto.Game

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

        MainPoster(newGamesViewState.value.randomOrNull() ?: Game.getDefaultInstance())

        GamesPreviewList(
            games = newGamesViewState.value,
            onMovieClicked = viewModel::onMovieClicked,
            onCategoryExpand = viewModel::onCategoryClick,
            titleText = "Новинки"
        )
    }
}

@Composable
fun MainPoster(game: Game) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        SubcomposeAsyncImage(
            model = game.artworksList[0].url,
            contentScale = ContentScale.Crop,
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    bottom = 16.dp
                )
                .align(Alignment.BottomStart)
        ) {
            //TODO check colors!
            Text(
                text = game.name,
                style = MaterialTheme.typography.subtitle1,
                color = Color.White,
            )
            Text(
                text = game.firstReleaseDate.toString(),
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
            )
        }
    }
}

@Composable
fun GamesPreviewList(
    games: List<Game>,
    onMovieClicked: (Context, Game) -> Unit,
    onCategoryExpand: (Context) -> Unit,
    titleText: String,
) {
    val currentContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 12.dp)
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
                GameListItem(game = game, onMovieClicked = onMovieClicked)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameListItem(game: Game, onMovieClicked: (Context, Game) -> Unit) {
    val currentContext = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .size(width = 120.dp, height = 160.dp)
            .clickable(
                onClick = { onMovieClicked(currentContext, game) }
            )
    ) {
        val (image, title, subtitle) = createRefs()
        if (game.hasCover()) {
            SubcomposeAsyncImage(
                model = game.cover.url,
                loading = { CircularProgressIndicator() },
                contentDescription = null,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
        else {
            Image(
                imageVector = ImageVector
                    .vectorResource(R.drawable.ic_baseline_home_24),
                contentDescription = null,
                modifier = Modifier
                    .background(color = MaterialTheme.colors.secondaryVariant)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
        Text(
            text = game.name,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.button,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(image.bottom)
                start.linkTo(image.start)
                end.linkTo(image.end)
            }
        )
        Text(
            text = game.firstReleaseDate.toString(),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f),
            style = MaterialTheme.typography.overline,
            textAlign = TextAlign.Start,
            modifier = Modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(title.start)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}