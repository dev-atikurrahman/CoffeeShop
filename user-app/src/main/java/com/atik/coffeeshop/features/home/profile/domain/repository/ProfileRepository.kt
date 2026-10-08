package com.atik.coffeeshop.features.home.profile.domain.repository

import com.atik.coffeeshop.features.auth.domain.models.User

interface ProfileRepository {
    suspend fun getProfile(): Result<User>
}