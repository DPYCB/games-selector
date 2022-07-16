package com.dpycb.gamesselector.presentation.games

data class GamesListViewState(
    val gamesListItems: List<GameListItem> = listOf()
)

data class GameListItem(
    val id: Long = 0L,
    val name: String = "",
    val genre: String = "",
    val imageRef: String = "",
)