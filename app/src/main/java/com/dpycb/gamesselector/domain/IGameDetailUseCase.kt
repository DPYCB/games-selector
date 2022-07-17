package com.dpycb.gamesselector.domain

import com.dpycb.gamesselector.presentation.games.GameDetailViewState
import kotlinx.coroutines.flow.Flow

interface IGameDetailUseCase {
    suspend fun getGameDetailViewState(gameId: Long): Flow<GameDetailViewState>
}