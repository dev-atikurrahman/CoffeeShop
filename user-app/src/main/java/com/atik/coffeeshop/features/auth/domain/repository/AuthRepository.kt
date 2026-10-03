package com.atik.coffeeshop.features.auth.domain.repository

import com.atik.coffeeshop.core.APIResponse
import com.atik.coffeeshop.features.auth.data.models.UserDto
import com.atik.coffeeshop.features.auth.domain.models.User
import retrofit2.http.Body

interface AuthRepository {
    suspend fun register(@Body request: User): Result<APIResponse<User>>
}

data class AuthSession(val user: User, val token: String)