package com.atik.coffeeshop.features.home.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atik.coffeeshop.core.ApiException
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileAction
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileDestination
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileDialog
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileEvent
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileMenuAction
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileUiState
import com.atik.coffeeshop.features.home.profile.domain.repository.ProfileRepository
import com.atik.coffeeshop.shared.data.preferences.UserPreferences
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreferences: UserPreferences,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    private val _events = Channel<ProfileEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            profileRepository.getProfile().fold(
                onSuccess = { user ->
                    _state.update { it.copy(
                        isLoading = false,
                        name = user.name,
                        email = user.email,
                        imageUrl = user.profile.profileImageUrl
                    )
                    }
                },
                onFailure = { e ->
                    val api = e as? ApiException
                    if (api?.statusCode == 401) {
                        userPreferences.clearSession()
                        _events.send(ProfileEvent.LoggedOut)
                    } else {
                        _state.update {
                            it.copy(isLoading = false, error = api?.message ?: "Something went wrong")
                        }
                    }
                }
            )
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.EditProfile -> navigate(ProfileDestination.EditProfile)

            is ProfileAction.MenuClick -> when (action.menu) {
                ProfileMenuAction.Settings -> navigate(ProfileDestination.Settings)
                ProfileMenuAction.Location -> navigate(ProfileDestination.Location)
                ProfileMenuAction.Payment -> navigate(ProfileDestination.Payment)
                ProfileMenuAction.History -> navigate(ProfileDestination.History)
                ProfileMenuAction.Notification -> navigate(ProfileDestination.Notification)
                ProfileMenuAction.Language -> showDialog(ProfileDialog.Language)
                ProfileMenuAction.Logout -> showDialog(ProfileDialog.LogoutConfirm)
            }

            is ProfileAction.LanguageSelected -> _state.update {
                it.copy(selectedLanguage = action.code, dialog = null)
            } // pore datastore e save korbe

            ProfileAction.ConfirmLogout -> viewModelScope.launch {
                _state.update { it.copy(dialog = null) }
                userPreferences.clearSession()
                _events.send(ProfileEvent.LoggedOut)
            }

            ProfileAction.DismissDialog -> _state.update { it.copy(dialog = null) }
            ProfileAction.Retry -> loadProfile()
        }
    }

    private fun showDialog(dialog: ProfileDialog) = _state.update { it.copy(dialog = dialog) }

    private fun navigate(destination: ProfileDestination) {
        viewModelScope.launch { _events.send(ProfileEvent.Navigate(destination)) }
    }
}