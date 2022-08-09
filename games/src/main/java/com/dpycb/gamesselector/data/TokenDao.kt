package com.dpycb.gamesselector.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TokenDao {
    @Query("SELECT * FROM authtoken ORDER BY expiresAt DESC")
    fun getCurrentToken(): AuthToken?

    @Insert
    fun addToken(token: AuthToken)

    @Query("DELETE FROM authtoken")
    fun clearTable()
}