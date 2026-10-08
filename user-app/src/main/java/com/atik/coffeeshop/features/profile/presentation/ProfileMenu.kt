package com.atik.coffeeshop.features.profile.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProfileMenuStyle { Default, Destructive }

@Immutable
data class ProfileMenuItemUi(
    val action: ProfileMenuAction,
    val icon: ImageVector,
    @StringRes val titleRes: Int,
    val style: ProfileMenuStyle = ProfileMenuStyle.Default
)