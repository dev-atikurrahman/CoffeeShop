package com.atik.coffeeshop.features.profile.presentation

import androidx.compose.runtime.Immutable

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

sealed interface ProfileDialog {
    data object Language : ProfileDialog
    data object LogoutConfirm : ProfileDialog
}