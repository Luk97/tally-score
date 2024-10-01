package com.nickel.tallyscore.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.nickel.tallyscore.data.UserPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val KEY_TEST = booleanPreferencesKey("key_test")
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
            val newTest = preferences[PreferencesKeys.KEY_TEST] ?: false
            UserPreferences(
                testBoolean = newTest
            )
        }

    suspend fun updateTestBoolean(testBoolean: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_TEST] = !testBoolean
        }
    }
}