package com.dpycb.gamesselector.presentation.gameslist

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpycb.gamesselector.domain.IGameDetailUseCase
import com.dpycb.gamesselector.domain.IGamesListUseCase
import com.dpycb.gamesselector.presentation.singlegame.GameDetailViewState
import com.dpycb.gamesselector.presentation.singlegame.SingleGameActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(
    private val gamesListUseCase: IGamesListUseCase,
    private val gameDetailUseCase: IGameDetailUseCase,
) : ViewModel() {
    private val newGamesListFlow = MutableStateFlow(GamesListViewState())
    private val gameDetailViewStateFlow = MutableStateFlow(GameDetailViewState())
    init {
        viewModelScope.launch {
            gamesListUseCase
                .getNewGamesList()
                .onEach { getGameDetailViewState(it.gamesListItems.random().id) }
                .collect(newGamesListFlow::emit)
        }
    }

    fun getNewGamesListFlow() = newGamesListFlow.asStateFlow()

    fun getMainPosterGameFlow() = gameDetailViewStateFlow.asStateFlow()

    fun onMovieClicked(context: Context, gameId: Long) {
        SingleGameActivity.create(context, gameId)
    }

    fun onCategoryClick(context: Context) {
        Toast.makeText(context, "Тут что-то откроется", Toast.LENGTH_SHORT).show()
    }

    private suspend fun getGameDetailViewState(gameId: Long) {
        gameDetailUseCase
            .getGameDetailViewState(gameId)
            .collect(gameDetailViewStateFlow::emit)
    }
}