package com.dpycb.gamesselector.data

import com.dpycb.gamesselector.domain.IGamesRepository
import javax.inject.Inject

class GamesRepository @Inject constructor(
    private val igdbServerDataSource: IGDBServerDataSource,
    private val authTokenProvider: AuthTokenProvider,
) : IGamesRepository {
    override suspend fun getNewestGames() = igdbServerDataSource.requestNewestGames(getValidToken())

    override suspend fun getSimilarGames(similarGames: String) = igdbServerDataSource.getSimilarGames(similarGames, getValidToken())

    override suspend fun getGameDetail(gameId: Long) = igdbServerDataSource.requestGameDetail(gameId, getValidToken())

    private suspend fun getValidToken() = authTokenProvider.getValidToken()
}