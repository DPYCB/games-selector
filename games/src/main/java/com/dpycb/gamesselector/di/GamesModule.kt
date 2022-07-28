package com.dpycb.gamesselector.di

import com.dpycb.gamesselector.data.GamesRepository
import com.dpycb.gamesselector.domain.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GamesModule {
    @Binds
    abstract fun bindGamesRepository(repo: GamesRepository): IGamesRepository

    @Binds
    abstract fun bindGamesListUseCase(useCase: GamesListUseCase) : IGamesListUseCase

    @Binds
    abstract fun bindGameDetailUseCase(useCase: GameDetailUseCase) : IGameDetailUseCase
}