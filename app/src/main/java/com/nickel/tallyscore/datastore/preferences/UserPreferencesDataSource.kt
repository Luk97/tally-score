package com.nickel.tallyscore.datastore.preferences

import androidx.datastore.core.DataStore
import com.nickel.tallyscore.data.UserPreferences
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val dataStore = userPreferences.data.map {
        UserPreferences(
            testBoolean = it.testBoolean
        )
    }
}