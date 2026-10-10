package com.atik.coffeeshop.shared.data.preferences

import com.atik.coffeeshop.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    val isOnboardingCompleted: Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)
    val isLoggedIn: Flow<Boolean>
    suspend fun setLoggedIn(loggedIn: Boolean)
    val authToken: Flow<String?>
    suspend fun saveSession(token: String, rememberMe: Boolean = true)
    suspend fun clearSession()
    suspend fun clearSessionIfNotRemembered()

    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)
}