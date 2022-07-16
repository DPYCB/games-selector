package com.dpycb.gamesselector.data.games

import com.dpycb.gamesselector.domain.games.IGamesRepository
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val igdbServerDataSource: IGDBServerDataSource,
) : IGamesRepository {
    override suspend fun getNewestGames() = igdbServerDataSource.requestNewestGames()
}