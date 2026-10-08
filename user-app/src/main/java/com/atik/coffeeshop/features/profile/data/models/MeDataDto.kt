package com.atik.coffeeshop.features.profile.data.models

import com.atik.coffeeshop.features.auth.data.models.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class MeDataDto(val user: UserDto)