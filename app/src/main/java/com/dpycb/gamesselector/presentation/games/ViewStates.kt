package com.dpycb.gamesselector.presentation.games

data class GamesListViewState(
    val gamesListItems: List<GameListItemViewState> = listOf()
)

data class GameListItemViewState(
    val id: Long = 0L,
    val name: String = "",
    val genre: String = "",
    val releaseDate: String = "",
    val platforms: String = "",
    val description: String = "",
    val imageRef: String = "",
)

data class GameDetailViewState (
    val id: Long = 0L,
    val name: String = "",
    val genre: String = "",
    val releaseDate: String = "",
    val platforms: String = "",
    val description: String = "",
    val coverRef: String = "",
    val screenshotsRef: List<String> = listOf(),
    val videoUrl: String = "",
    val rating: Double = 0.0,
    val ratingCount: Int = 0,
    val similarGames: GamesListViewState = GamesListViewState()
)