package com.dpycb.gamesselector.presentation.games

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpycb.gamesselector.domain.games.IGamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import proto.Game
import javax.inject.Inject

@HiltViewModel
class GamesListViewModel @Inject constructor(
    private val gamesRepository: IGamesRepository,
) : ViewModel() {
    private val newGamesListFlow = MutableStateFlow<List<Game>>(listOf())
    init {
        viewModelScope.launch {
            gamesRepository
                .requestGames()
                .collect(newGamesListFlow::emit)
        }
    }

    fun getNewGamesListFlow() = newGamesListFlow.asStateFlow()

    fun onMovieClicked(context: Context, game: Game) {
        Toast.makeText(context, "open single game ${game.id} screen", Toast.LENGTH_SHORT).show()
    }

    fun onCategoryClick(context: Context) {
        Toast.makeText(context, "open single game screen", Toast.LENGTH_SHORT).show()
    }

}