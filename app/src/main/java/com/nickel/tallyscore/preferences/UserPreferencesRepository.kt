package com.nickel.tallyscore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object PreferencesKeys {
        val KEY_BOARD_SIZE = stringPreferencesKey("key_board_size")
    }

    val userPreferences = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            UserPreferences(
                boardSize = getBoardSize(preferences)
            )
        }

    suspend fun updateBoardSize(boardSize: UserPreferences.BoardSize) {
        dataStore.edit { preferences ->
            preferences[KEY_BOARD_SIZE] = boardSize.name
        }
    }

    private fun getBoardSize(preferences: Preferences) =
        preferences[KEY_BOARD_SIZE]?.let { value ->
            UserPreferences.BoardSize.entries.first { it.name == value }
        } ?: UserPreferences.BoardSize.MEDIUM
}