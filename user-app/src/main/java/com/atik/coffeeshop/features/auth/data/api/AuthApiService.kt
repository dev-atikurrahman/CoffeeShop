package com.atik.coffeeshop.features.auth.data.api

import com.atik.coffeeshop.core.APIResponse
import com.atik.coffeeshop.data.remote.api.ApiConstants
import com.atik.coffeeshop.features.auth.data.models.UserDto
import com.atik.coffeeshop.features.auth.domain.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST(ApiConstants.ENDPOINT_REGISTRATION)
    suspend fun register(@Body request: UserDto): Response<APIResponse<User>>
}