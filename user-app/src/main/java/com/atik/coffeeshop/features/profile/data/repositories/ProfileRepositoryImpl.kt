package com.atik.coffeeshop.features.profile.data.repositories

import com.atik.coffeeshop.core.network.safeApiCall
import com.atik.coffeeshop.features.auth.domain.models.User
import com.atik.coffeeshop.features.auth.domain.models.toDomain
import com.atik.coffeeshop.features.profile.data.api.ProfileApiService
import com.atik.coffeeshop.features.profile.domain.repository.ProfileRepository
import kotlinx.serialization.json.Json

class ProfileRepositoryImpl(
    private val api: ProfileApiService,
    private val json: Json
) : ProfileRepository {

    override suspend fun getProfile(): Result<User> =
        safeApiCall(json) { api.getMe() }.map { it.user.toDomain() }


}