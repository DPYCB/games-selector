package com.dpycb.gamesselector.domain

import com.dpycb.gamesselector.presentation.games.GamesListViewState
import kotlinx.coroutines.flow.Flow

interface IGamesListUseCase {
    suspend fun getNewGamesList(): Flow<GamesListViewState>
    suspend fun getSimilarGames(similarGames: List<Long>): Flow<GamesListViewState>
}