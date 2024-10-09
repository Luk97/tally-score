package com.nickel.tallyscore.persistence.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.nickel.tallyscore.utils.ContextProvider
import com.nickel.tallyscore.design.localcomposistions.AppTheme
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

internal class PreferencesDatastoreProvider: PreferenceProvider {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { ContextProvider.context.preferencesDataStoreFile(USER_PREFERENCES_NAME) },
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        )
    )

    private companion object PreferencesKeys {
        const val USER_PREFERENCES_NAME = "user_preferences"
        val KEY_ZOOM_LEVEL = floatPreferencesKey("key_zoom_level")
        val KEY_APP_THEME = stringPreferencesKey("key_app_theme")
    }

    override val userPreferences = dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                UserPreferences(
                    zoomLevel = preferences[KEY_ZOOM_LEVEL] ?: 1f,
                    appTheme = AppTheme.valueOf(preferences[KEY_APP_THEME] ?: AppTheme.SYSTEM.name)
                )
            }

    override suspend fun updateZoomLevel(zoomLevel: Float) {
        dataStore.edit { preferences ->
            preferences[KEY_ZOOM_LEVEL] = zoomLevel
        }
    }

    override suspend fun updateAppTheme(appTheme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[KEY_APP_THEME] = appTheme.name
        }
    }
}