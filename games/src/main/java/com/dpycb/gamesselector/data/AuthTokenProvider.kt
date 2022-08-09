package com.dpycb.gamesselector.data

import com.api.igdb.request.TwitchAuthenticator
import com.dpycb.gamesselector.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthTokenProvider @Inject constructor(
    private val authTokenLocalDataSource: AuthTokenLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getValidToken() = withContext(ioDispatcher) {
        val localToken = authTokenLocalDataSource.getLocalToken()
        when {
            localToken != null && localToken.expiresAt > System.currentTimeMillis() -> localToken.accessToken
            TwitchAuthenticator.twitchToken == null -> {
                authTokenLocalDataSource.dropTokens()
                TwitchAuthenticator.requestTwitchToken(
                    IGDBServerDataSource.CLIENT_ID,
                    IGDBServerDataSource.CLIENT_SECRET
                )?.also(authTokenLocalDataSource::addToken)?.access_token ?: ""
            }
            else -> TwitchAuthenticator.twitchToken?.access_token ?: ""
        }
    }
}