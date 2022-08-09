package com.dpycb.gamesselector.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthToken(
    @PrimaryKey
    var uid: Long = 0L,
    val accessToken: String,
    val expiresAt: Long,
    val tokenType: String
)