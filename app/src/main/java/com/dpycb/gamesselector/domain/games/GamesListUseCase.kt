package com.dpycb.gamesselector.domain.games

import android.util.Log
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.dpycb.gamesselector.presentation.games.GameListItem
import com.dpycb.gamesselector.presentation.games.GamesListViewState
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import proto.Game
import javax.inject.Inject

class GamesListUseCase @Inject constructor(
    private val gamesRepository: IGamesRepository,
) : IGamesListUseCase {
    override suspend fun getNewGamesList() = gamesRepository
        .getNewestGames()
        .map { rawGames ->
            GamesListViewState(
                rawGames.map { rawGame ->
                    rawGame.mapToGameListItem()
                }
            )
        }

    private fun Game.mapToGameListItem(): GameListItem {
        val genre = try {
            this.genresList[0]?.name ?: ""
        } catch (e: Throwable) {
            ""
        }
        return GameListItem(
            id = this.id,
            name = this.name,
            genre = genre,
            imageRef = parseImageRef(this)
        )
    }

    private fun parseImageRef(rawGame: Game) = when {
        rawGame.hasCover() -> imageBuilder(rawGame.cover.imageId, ImageSize.COVER_BIG, ImageType.WEBP)
        else -> ""
    }
}