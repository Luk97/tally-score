package com.nickel.tallyscore.player

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    @Provides
    @Singleton
    fun providePlayerDatabase(app: Application): PlayerDatabase = Room
        .databaseBuilder(app, PlayerDatabase::class.java, "player_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providePlayerRepository(db: PlayerDatabase): PlayerRepository =
        PlayerRepository(db.dao)

}