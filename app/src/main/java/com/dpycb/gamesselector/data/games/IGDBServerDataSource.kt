package com.dpycb.gamesselector.data.games

import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.TwitchAuthenticator
import com.api.igdb.request.games
import com.api.igdb.utils.TwitchToken
import com.dpycb.gamesselector.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject

class IGDBServerDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    companion object {
        private const val CLIENT_ID = "3wexez9y24n5nhhxk52lhtmr9xzas9"
        private const val CLIENT_SECRET = "l5zkwqa1ab957611jofin5z4vqeqh8"
    }
    private suspend fun requestTwitchToken(): TwitchToken? {
        return coroutineScope {
            withContext(ioDispatcher) {
                TwitchAuthenticator.requestTwitchToken(CLIENT_ID, CLIENT_SECRET)
            }
        }
    }

    suspend fun requestSomeGames(): Flow<List<Game>> = flow {
        val twitchToken = requestTwitchToken()?.access_token ?: return@flow
        IGDBWrapper.setCredentials(CLIENT_ID, twitchToken)
        val apicalypse = APICalypse().fields("*").sort("release_dates.date", Sort.DESCENDING)
        try{
            emit(IGDBWrapper.games(apicalypse))
        } catch(e: RequestException) {
            Log.e("IGDB_ERROR", e.stackTraceToString())
            emit(listOf())
        }
    }.flowOn(ioDispatcher)
}