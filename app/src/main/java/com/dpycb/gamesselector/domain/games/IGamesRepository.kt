package com.dpycb.gamesselector.domain.games

import kotlinx.coroutines.flow.Flow
import proto.Game

interface IGamesRepository {
    suspend fun getNewestGames(): Flow<List<Game>>
}