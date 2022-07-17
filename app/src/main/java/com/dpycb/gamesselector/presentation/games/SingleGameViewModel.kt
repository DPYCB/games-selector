package com.dpycb.gamesselector.presentation.games

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpycb.gamesselector.domain.IGameDetailUseCase
import com.dpycb.gamesselector.domain.IGamesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleGameViewModel @Inject constructor(
    private val gameDetailUseCase: IGameDetailUseCase,
    private val gameListUseCase: IGamesListUseCase,
) : ViewModel() {
    private val gameDetailViewStateFlow = MutableStateFlow(GameDetailViewState())
    private val similarGamesListViewState = MutableStateFlow(GamesListViewState())

    fun getGameDetailViewStateFlow() = gameDetailViewStateFlow.asStateFlow()

    fun getSimilarGamesViewState() = similarGamesListViewState.asStateFlow()

    fun updateGameDetailViewState(gameId: Long) {
        viewModelScope.launch {
            gameDetailUseCase
                .getGameDetailViewState(gameId)
                .collect(gameDetailViewStateFlow::emit)
        }
    }

    fun updateSimilarGamesListViewState(similarGameIds: List<Long>) {
        viewModelScope.launch {
            gameListUseCase
                .getSimilarGames(similarGameIds)
                .collect(similarGamesListViewState::emit)
        }
    }

    fun markGame(context: Context, gameId: Long) {
        Toast.makeText(context, "Игра id=$gameId будет куда-то добавлена", Toast.LENGTH_SHORT).show()
    }

    fun onSimilarMovieClicked(context: Context, gameId: Long) {
        Toast.makeText(context, "Тут откроется такой же экран для игры $gameId", Toast.LENGTH_SHORT).show()
    }
}