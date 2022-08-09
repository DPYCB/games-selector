package com.dpycb.gamesselector.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AuthToken::class], version = 1)
abstract class RoomDb: RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}