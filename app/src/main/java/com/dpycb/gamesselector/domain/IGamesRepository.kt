package com.dpycb.gamesselector.domain

import kotlinx.coroutines.flow.Flow
import proto.Game

interface IGamesRepository {
    suspend fun getNewestGames(): Flow<List<Game>>
    suspend fun getGameDetail(gameId: Long): Flow<Game>
}