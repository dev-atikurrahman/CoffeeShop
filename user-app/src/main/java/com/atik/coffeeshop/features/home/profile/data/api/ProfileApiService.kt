package com.atik.coffeeshop.features.home.profile.data.api

import com.atik.coffeeshop.data.remote.api.ApiConstants
import com.atik.coffeeshop.features.auth.data.models.ApiEnvelope
import com.atik.coffeeshop.features.home.profile.data.models.MeDataDto
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApiService {
    @GET(ApiConstants.ENDPOINT_ME)
    suspend fun getMe(): Response<ApiEnvelope<MeDataDto>>
}