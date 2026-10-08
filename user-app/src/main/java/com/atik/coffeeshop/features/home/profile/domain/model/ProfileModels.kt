package com.atik.coffeeshop.features.home.profile.domain.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProfileDestination { Settings, Location, Payment, History, Notification, EditProfile }

enum class ProfileMenuAction { Settings, Location, Payment, History, Language, Notification, Logout }

sealed interface ProfileDialog {
    data object Language : ProfileDialog
    data object LogoutConfirm : ProfileDialog
}

sealed interface ProfileAction {
    data object EditProfile : ProfileAction
    data class MenuClick(val menu: ProfileMenuAction) : ProfileAction
    data class LanguageSelected(val code: String) : ProfileAction
    data object ConfirmLogout : ProfileAction
    data object DismissDialog : ProfileAction
    data object Retry: ProfileAction
}

sealed interface ProfileEvent {
    data class Navigate(val destination: ProfileDestination) : ProfileEvent
    data object LoggedOut : ProfileEvent
}

enum class ProfileMenuStyle { Default, Destructive }

@Immutable
data class ProfileMenuItemUi(
    val action: ProfileMenuAction,
    val icon: ImageVector,
    @StringRes val titleRes: Int,
    val style: ProfileMenuStyle = ProfileMenuStyle.Default
)

@Immutable
data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val imageUrl: String? = null,
    val selectedLanguage: String = "bn",
    val isLoading: Boolean = true,
    val error: String? = null,
    val dialog: ProfileDialog? = null
)