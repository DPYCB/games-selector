package com.dpycb.gamesselector.data.games

import com.dpycb.gamesselector.domain.IGamesRepository
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val igdbServerDataSource: IGDBServerDataSource,
) : IGamesRepository {
    override suspend fun getNewestGames() = igdbServerDataSource.requestNewestGames()

    override suspend fun getSimilarGames(similarGames: String) = igdbServerDataSource.getSimilarGames(similarGames)

    override suspend fun getGameDetail(gameId: Long) = igdbServerDataSource.requestGameDetail(gameId)
}