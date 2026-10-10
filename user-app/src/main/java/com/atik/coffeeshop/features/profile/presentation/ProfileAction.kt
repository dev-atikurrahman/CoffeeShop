package com.atik.coffeeshop.features.profile.presentation

enum class ProfileMenuAction { Settings, Location, Payment, History, Language, Notification, Theme, Logout }

sealed interface ProfileAction {
    data object EditProfile : ProfileAction
    data class MenuClick(val menu: ProfileMenuAction) : ProfileAction
    data class LanguageSelected(val code: String) : ProfileAction
    data class ThemeSelected(val code: String) : ProfileAction
    data object ConfirmLogout : ProfileAction
    data object DismissDialog : ProfileAction
    data object Retry : ProfileAction
}