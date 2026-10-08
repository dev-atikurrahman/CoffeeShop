package com.atik.coffeeshop.features.auth.data.api

import com.atik.coffeeshop.core.network.ApiEnvelope
import com.atik.coffeeshop.data.remote.api.ApiConstants
import com.atik.coffeeshop.features.auth.data.models.AuthDataDto
import com.atik.coffeeshop.features.auth.data.models.LoginRequestDto
import com.atik.coffeeshop.features.auth.data.models.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST(ApiConstants.ENDPOINT_REGISTER)
    suspend fun register(@Body body: RegisterRequestDto): Response<ApiEnvelope<AuthDataDto>>

    @POST(ApiConstants.ENDPOINT_LOGIN)
    suspend fun login(@Body body: LoginRequestDto): Response<ApiEnvelope<AuthDataDto>>
}