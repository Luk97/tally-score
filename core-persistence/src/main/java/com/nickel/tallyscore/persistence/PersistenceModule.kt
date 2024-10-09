package com.nickel.tallyscore.persistence

import android.app.Application
import androidx.room.Room
import com.nickel.tallyscore.persistence.player.PlayerDatabase
import com.nickel.tallyscore.persistence.player.PlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    internal fun providePlayerDatabase(app: Application): PlayerDatabase = Room
        .databaseBuilder(app, PlayerDatabase::class.java, "player_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providePlayerRepository(db: PlayerDatabase): PlayerRepository =
        PlayerRepository(db.dao)
}