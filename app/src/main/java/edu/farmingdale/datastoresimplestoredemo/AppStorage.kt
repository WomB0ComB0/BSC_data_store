package edu.farmingdale.datastoresimplestoredemo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import edu.farmingdale.datastoresimplestoredemo.data.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppStorage(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

    val appPreferenceFlow: Flow<AppPreferences> = context.dataStore.data
        .map { preferences ->
            AppPreferences(
                userName = preferences[USER_NAME] ?: "",
                highScore = preferences[HIGH_SCORE] ?: 0,
                darkMode = preferences[DARK_MODE] ?: false
            )
        }

    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = username
        }
    }

    suspend fun saveHighScore(score: Int) {
        context.dataStore.edit { preferences ->
            preferences[HIGH_SCORE] = score
        }
    }

    suspend fun saveDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE] = isDarkMode
        }
    }

    companion object {
        private const val PREFERENCES_NAME = "app_preferences"
        private val USER_NAME = stringPreferencesKey("user_name")
        private val HIGH_SCORE = intPreferencesKey("high_score")
        private val DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}
