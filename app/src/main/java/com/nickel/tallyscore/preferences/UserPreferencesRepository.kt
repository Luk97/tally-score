package com.nickel.tallyscore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object PreferencesKeys {
        val KEY_ZOOM_LEVEL = floatPreferencesKey("key_zoom_level")
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
                zoomLevel = preferences[KEY_ZOOM_LEVEL] ?: 1f
            )
        }

    suspend fun updateZoomLevel(zoomLevel: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_ZOOM_LEVEL] = zoomLevel
        }
    }
}