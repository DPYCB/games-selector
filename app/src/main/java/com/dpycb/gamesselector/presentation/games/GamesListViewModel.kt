package com.dpycb.gamesselector.presentation.games

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpycb.gamesselector.domain.IGamesListUseCase
import com.dpycb.gamesselector.presentation.SingleGameActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(
    private val gamesListUseCase: IGamesListUseCase,
) : ViewModel() {
    private val newGamesListFlow = MutableStateFlow(GamesListViewState())
    init {
        viewModelScope.launch {
            gamesListUseCase
                .getNewGamesList()
                .collect(newGamesListFlow::emit)
        }
    }

    fun getNewGamesListFlow() = newGamesListFlow.asStateFlow()

    fun onMovieClicked(context: Context, gameId: Long) {
        SingleGameActivity.create(context, gameId)
    }

    fun onCategoryClick(context: Context) {
        Toast.makeText(context, "Тут что-то откроется", Toast.LENGTH_SHORT).show()
    }
}