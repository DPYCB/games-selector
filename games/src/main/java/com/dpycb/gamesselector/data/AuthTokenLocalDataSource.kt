package com.dpycb.gamesselector.data

import com.api.igdb.utils.TwitchToken
import javax.inject.Inject

class AuthTokenLocalDataSource @Inject constructor(
    private val authTokenDao: TokenDao,
) {
    fun getLocalToken() = authTokenDao.getCurrentToken()

    fun dropTokens() = authTokenDao.clearTable()

    fun addToken(twitchToken: TwitchToken) = AuthToken(
        accessToken = twitchToken.access_token,
        expiresAt = twitchToken.expires_in + System.currentTimeMillis(),
        tokenType = twitchToken.token_type
    ).let(authTokenDao::addToken)
}