package com.atik.coffeeshop.features.profile.presentation

enum class ProfileDestination { Settings, Location, Payment, History, Notification, EditProfile }

sealed interface ProfileEvent {
    data class Navigate(val destination: ProfileDestination) : ProfileEvent
    data object LoggedOut : ProfileEvent
}