package com.atik.coffeeshop.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atik.coffeeshop.features.auth.presentation.login.LoginViewModel.Companion.AUTH_TAG
import com.atik.coffeeshop.shared.data.preferences.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch


sealed interface StartDestination {
    data object Loading : StartDestination
    data object Onboarding : StartDestination
    data object Login : StartDestination
    data object Home : StartDestination
}

class SplashViewModel(
    userPreferences: UserPreferences
) : ViewModel() {
    private val _startDestination = MutableStateFlow<StartDestination>(StartDestination.Loading)
    val startDestination: StateFlow<StartDestination> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            userPreferences.clearSessionIfNotRemembered()
            combine(
                userPreferences.isOnboardingCompleted,
                userPreferences.isLoggedIn,
            ) { onboardingDone, loggedIn ->
                when {
                    !onboardingDone -> StartDestination.Onboarding
                    !loggedIn -> StartDestination.Login
                    else -> StartDestination.Home
                }
            }.collect { destination ->
                Log.d(AUTH_TAG, "SplashViewModel: emitting new destination = $destination")
                _startDestination.value = destination
            }
        }
    }

}