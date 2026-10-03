package com.atik.coffeeshop.features.auth.domain.repository

import com.atik.coffeeshop.features.auth.domain.models.AuthSession

interface AuthRepository {
    suspend fun register(name: String, email: String, password: String): Result<AuthSession>
    suspend fun login(email: String, password: String): Result<AuthSession>
}
