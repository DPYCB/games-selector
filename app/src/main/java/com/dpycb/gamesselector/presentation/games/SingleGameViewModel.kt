package com.dpycb.gamesselector.presentation.games

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpycb.gamesselector.domain.IGameDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleGameViewModel @Inject constructor(
    private val gameDetailUseCase: IGameDetailUseCase,
) : ViewModel() {
    private val gameDetailViewStateFlow = MutableStateFlow(GameDetailViewState())

    fun getGameDetailViewStateFlow() = gameDetailViewStateFlow.asStateFlow()

    fun updateGameDetailViewState(gameId: Long) {
        viewModelScope.launch {
            gameDetailUseCase
                .getGameDetailViewState(gameId)
                .collect {
                    gameDetailViewStateFlow.value = it
                }
        }
    }

    fun markGame(context: Context, gameId: Long) {
        Toast.makeText(context, "Игра id=$gameId будет куда-то добавлена", Toast.LENGTH_SHORT).show()
    }
}