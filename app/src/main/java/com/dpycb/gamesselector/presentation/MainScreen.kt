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
import com.dpycb.gamesselector.presentation.games.GameListItem
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

        MainPoster(newGamesViewState.value.gamesListItems.randomOrNull() ?: GameListItem())

        GamesPreviewList(
            games = newGamesViewState.value.gamesListItems,
            onMovieClicked = viewModel::onMovieClicked,
            onCategoryExpand = viewModel::onCategoryClick,
            titleText = "Новинки"
        )
    }
}

@Composable
fun MainPoster(game: GameListItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        SubcomposeAsyncImage(
            model = game.imageRef,
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
                text = game.genre,
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
            )
        }
    }
}

@Composable
fun GamesPreviewList(
    games: List<GameListItem>,
    onMovieClicked: (Context, GameListItem) -> Unit,
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
fun GameListItemView(game: GameListItem, onMovieClicked: (Context, GameListItem) -> Unit) {
    val currentContext = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .size(width = 120.dp, height = 160.dp)
            .clickable(
                onClick = { onMovieClicked(currentContext, game) }
            )
    ) {
        val (image, title, subtitle) = createRefs()
        SubcomposeAsyncImage(
            model = game.imageRef,
            loading = { CircularProgressIndicator() },
            contentDescription = null,
            modifier = Modifier
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen()
}