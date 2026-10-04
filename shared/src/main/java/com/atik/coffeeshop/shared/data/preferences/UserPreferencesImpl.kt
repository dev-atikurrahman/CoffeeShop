package com.atik.coffeeshop.shared.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : UserPreferences {

    private object Keys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val LOGGED_IN = booleanPreferencesKey("logged_in")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    private val safeData: Flow<Preferences> = dataStore.data.catch { exception ->
        if (exception is IOException) emit(emptyPreferences()) else throw exception
    }

    override val isOnboardingCompleted: Flow<Boolean> =
        safeData.map { prefs -> prefs[Keys.ONBOARDING_COMPLETED] ?: false }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.ONBOARDING_COMPLETED] = completed }
    }

    override val isLoggedIn: Flow<Boolean> =
        safeData.map { prefs -> prefs[Keys.LOGGED_IN] ?: false }

    override suspend fun setLoggedIn(loggedIn: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.LOGGED_IN] = loggedIn }
    }

    override val authToken: Flow<String?> =
        safeData.map { prefs -> prefs[Keys.AUTH_TOKEN] }

    override suspend fun saveSession(token: String, rememberMe: Boolean) {
        dataStore.edit { prefs ->
            prefs[Keys.AUTH_TOKEN] = token
            prefs[Keys.LOGGED_IN] = true
            prefs[Keys.ONBOARDING_COMPLETED] = true
            prefs[Keys.REMEMBER_ME] = rememberMe
        }
    }

    override suspend fun clearSession() {
        dataStore.edit { prefs ->
            prefs.remove(Keys.LOGGED_IN)
            prefs.remove(Keys.AUTH_TOKEN)
            prefs.remove(Keys.REMEMBER_ME)
        }
    }

    override suspend fun clearSessionIfNotRemembered() {
        dataStore.edit { prefs ->
            if (prefs[Keys.REMEMBER_ME] != true) {
                prefs.remove(Keys.LOGGED_IN)
                prefs.remove(Keys.AUTH_TOKEN)
                prefs.remove(Keys.REMEMBER_ME)
            }
        }
    }
}