package com.atik.coffeeshop.data.remote.interceptor

import com.atik.coffeeshop.data.remote.api.ApiConstants
import com.atik.coffeeshop.shared.data.preferences.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val userPreferences: UserPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { userPreferences.authToken.first() }

        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrBlank()) {
                header(ApiConstants.HEADER_AUTHORIZATION, ApiConstants.getBearerToken(token))
            }
        }.build()
        return chain.proceed(request)
    }
}