package com.nickel.tallyscore.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.nickel.tallyscore.datastore.playerdatabase.PlayerDatabase
import com.nickel.tallyscore.datastore.playerdatabase.PlayerRepository
import com.nickel.tallyscore.datastore.preferences.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

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

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES_NAME) },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            )
        )

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>) =
        UserPreferencesRepository(dataStore)
}