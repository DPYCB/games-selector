package com.dpycb.gamesselector.data

import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.*
import com.api.igdb.utils.TwitchToken
import com.dpycb.gamesselector.di.IoDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import proto.Game
import javax.inject.Inject

class IGDBServerDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    companion object {
        const val CLIENT_ID = "3wexez9y24n5nhhxk52lhtmr9xzas9"
        const val CLIENT_SECRET = "l5zkwqa1ab957611jofin5z4vqeqh8"
        private const val GAME_LIST_FIELDS = "*, cover.image_id, genres.*, platforms.*"
        private const val GAME_DETAIL_FIELDS = GAME_LIST_FIELDS +
                ", screenshots.image_id, videos.video_id, rating, rating_count"
    }

    suspend fun requestNewestGames(accessToken: String): Flow<List<Game>> = flow {
        IGDBWrapper.setCredentials(CLIENT_ID, accessToken)
        val apicalypse = APICalypse()
            .fields(GAME_LIST_FIELDS)
            .limit(20)
            .sort("release_dates.date", Sort.DESCENDING)
        try{
            emit(IGDBWrapper.games(apicalypse))
        } catch(e: RequestException) {
            Log.e("IGDB_ERROR", e.stackTraceToString())
            emit(listOf())
        }
    }.flowOn(ioDispatcher)


    suspend fun requestGameDetail(gameId: Long, accessToken: String): Flow<Game> = flow {
        IGDBWrapper.setCredentials(CLIENT_ID, accessToken)
        val apicalypse = APICalypse()
            .fields(GAME_DETAIL_FIELDS)
            .where("id = $gameId")
            .limit(1)
        try{
            emit(IGDBWrapper.games(apicalypse)[0])
        } catch(e: RequestException) {
            Log.e("IGDB_ERROR", e.stackTraceToString())
            emit(Game.getDefaultInstance())
        }
    }.flowOn(ioDispatcher)

    suspend fun requestSimilarGames(similarGames: String, accessToken: String): Flow<List<Game>> = flow {
        IGDBWrapper.setCredentials(CLIENT_ID, accessToken)
        val apicalypse = APICalypse()
            .fields(GAME_LIST_FIELDS)
            .where("id = $similarGames")
            .limit(20)
            .sort("release_dates.date", Sort.DESCENDING)
        try{
            emit(IGDBWrapper.games(apicalypse))
        } catch(e: RequestException) {
            Log.e("IGDB_ERROR", e.stackTraceToString())
            emit(listOf())
        }
    }.flowOn(ioDispatcher)
}