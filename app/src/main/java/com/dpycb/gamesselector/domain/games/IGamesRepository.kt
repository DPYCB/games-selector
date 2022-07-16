package com.dpycb.gamesselector.domain.games

import kotlinx.coroutines.flow.Flow
import proto.Game

interface IGamesRepository {
    suspend fun requestGames(): Flow<List<Game>>
}