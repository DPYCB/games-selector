package com.dpycb.gamesselector.domain

import com.dpycb.gamesselector.presentation.games.GameListItemViewState
import com.dpycb.gamesselector.presentation.games.GamesListViewState
import kotlinx.coroutines.flow.map
import proto.Game
import javax.inject.Inject

class GamesListUseCase @Inject constructor(
    private val gamesRepository: IGamesRepository,
) : IGamesListUseCase {
    override suspend fun getNewGamesList() = gamesRepository
        .getNewestGames()
        .map { GamesListViewState(it.filter { it.cover.imageId.isNotEmpty() }.map(::mapToGameListItem)) }

    override suspend fun getSimilarGames(similarGames: List<Long>) = gamesRepository
        .getSimilarGames(parseGameIdsToRequestString(similarGames))
        .map { GamesListViewState(it.filter { it.cover.imageId.isNotEmpty() }.map(::mapToGameListItem)) }

    private fun mapToGameListItem(rawGame: Game) = GameListItemViewState(
            id = rawGame.id,
            name = rawGame.name,
            genre = parseGenre(rawGame.genresList),
            releaseDate = parseReleaseDate(rawGame.firstReleaseDate),
            platforms = parsePlatforms(rawGame.platformsList),
            description = rawGame.summary,
            imageRef = parseImageRef(rawGame)
        )
}