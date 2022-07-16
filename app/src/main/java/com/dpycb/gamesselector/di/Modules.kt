package com.dpycb.gamesselector.di

import com.dpycb.gamesselector.data.games.GamesRepository
import com.dpycb.gamesselector.domain.games.GamesListUseCase
import com.dpycb.gamesselector.domain.games.IGamesListUseCase
import com.dpycb.gamesselector.domain.games.IGamesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class GamesBindsModule {
    @Binds
    abstract fun bindGamesRepository(repo: GamesRepository): IGamesRepository

    @Binds
    abstract fun bindGamesListUseCase(useCase: GamesListUseCase) : IGamesListUseCase
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @Provides
    fun provideExternalCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher