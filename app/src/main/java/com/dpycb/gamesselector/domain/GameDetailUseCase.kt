package com.dpycb.gamesselector.domain

import com.dpycb.gamesselector.presentation.games.GameDetailViewState
import kotlinx.coroutines.flow.map
import proto.Game
import javax.inject.Inject

class GameDetailUseCase @Inject constructor(
    private val gamesRepository: IGamesRepository,
) : IGameDetailUseCase {
    override suspend fun getGameDetailViewState(gameId: Long) = gamesRepository
        .getGameDetail(gameId)
        .map(::mapToGameDetailViewState)

    //TODO add parsing similar games with cover refs
    private fun mapToGameDetailViewState(game: Game) = GameDetailViewState(
        id = game.id,
        name = game.name,
        genre = parseGenre(game.genresList),
        releaseDate = parseReleaseDate(game.firstReleaseDate),
        platforms = parsePlatforms(game.platformsList),
        description = game.summary,
        coverRef = parseImageRef(game),
        screenshotsRef = parseScreenshots(game.screenshotsList),
        videoUrl = parseVideoUrl(game.videosList),
        rating = game.rating,
        ratingCount = game.ratingCount
    )
}