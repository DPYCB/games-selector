package com.dpycb.gamesselector.domain.games

import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.dpycb.gamesselector.presentation.games.GameListItemViewState
import com.dpycb.gamesselector.presentation.games.GamesListViewState
import com.google.protobuf.Timestamp
import kotlinx.coroutines.flow.map
import proto.Game
import proto.Platform
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class GamesListUseCase @Inject constructor(
    private val gamesRepository: IGamesRepository,
) : IGamesListUseCase {
    override suspend fun getNewGamesList() = gamesRepository
        .getNewestGames()
        .map { GamesListViewState(it.filter { it.cover.imageId.isNotEmpty() }.map(::mapToGameListItem)) }

    private fun mapToGameListItem(rawGame: Game): GameListItemViewState {
        val genre = try {
            rawGame.genresList[0]?.name ?: ""
        } catch (e: Throwable) {
            ""
        }
        return GameListItemViewState(
            id = rawGame.id,
            name = rawGame.name,
            genre = genre,
            releaseDate = parseReleaseDate(rawGame.firstReleaseDate),
            platforms = parsePlatforms(rawGame.platformsList),
            description = rawGame.summary,
            imageRef = parseImageRef(rawGame)
        )
    }

    private fun parseReleaseDate(date: Timestamp): String {
        return SimpleDateFormat("dd.MM.yyyy")
            .format(Date(date.seconds * 1000))
    }

    private fun parsePlatforms(platforms: List<Platform>): String {
        var result = ""
        platforms.forEach { result += it.name }
        return result
    }

    private fun parseImageRef(rawGame: Game) = when {
        rawGame.hasCover() -> imageBuilder(rawGame.cover.imageId, ImageSize.COVER_BIG, ImageType.WEBP)
        else -> ""
    }
}