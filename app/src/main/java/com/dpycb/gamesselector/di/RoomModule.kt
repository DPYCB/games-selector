package com.dpycb.gamesselector.di

import android.content.Context
import androidx.room.Room
import com.dpycb.gamesselector.data.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): RoomDb {
        return Room.databaseBuilder(
            context,
            RoomDb::class.java,
            "gamesselector_room-db"
        ).build()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object AuthProvidesModule {
    @Provides
    fun provideChannelDao(roomDb: RoomDb) = roomDb.tokenDao()
}