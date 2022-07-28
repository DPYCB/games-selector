package com.dpycb.gamesselector.domain

import com.dpycb.gamesselector.presentation.gameslist.GamesListViewState
import kotlinx.coroutines.flow.Flow

interface IGamesListUseCase {
    suspend fun getNewGamesList(): Flow<GamesListViewState>
    suspend fun getSimilarGames(similarGames: List<Long>): Flow<GamesListViewState>
}