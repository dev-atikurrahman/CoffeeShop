package com.atik.coffeeshop.features.auth.data.repositories

import com.atik.coffeeshop.core.network.safeApiCall
import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import com.atik.coffeeshop.features.auth.data.models.AuthDataDto
import com.atik.coffeeshop.features.auth.data.models.LoginRequestDto
import com.atik.coffeeshop.features.auth.data.models.RegisterRequestDto
import com.atik.coffeeshop.features.auth.domain.models.AuthSession
import com.atik.coffeeshop.features.auth.domain.models.toDomain
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
import kotlinx.serialization.json.Json

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val json: Json
) : AuthRepository {

    companion object {
        private const val TAG = "AuthDebug"
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<AuthSession> =
        safeApiCall(json) {
            api.register(RegisterRequestDto(name.trim(), email.trim(), password))
        }.map { it.toSession() }

    override suspend fun login(email: String, password: String): Result<AuthSession> =
        safeApiCall(json) {
            api.login(LoginRequestDto(email.trim(), password))
        }.map { it.toSession() }

    private fun AuthDataDto.toSession() = AuthSession(user.toDomain(), token)
}











