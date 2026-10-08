package com.atik.coffeeshop.data.remote.api

object ApiConstants {
    /** --- Base URLs ---*/
    const val BASE_URL = "http://192.168.11.37/coffeeshop_api/public/"

    /** --- Network Configurations ---*/
    const val TIMEOUT_CONNECT = 10L
    const val TIMEOUT_READ = 10L
    const val TIMEOUT_WRITE = 10L

    /** --- API Endpoints ---*/
    const val ENDPOINT_REGISTER = "api/auth/register"
    const val ENDPOINT_LOGIN = "api/auth/login"
    const val ENDPOINT_ME = "api/me"

    /** --- Header Keys ---*/
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_ACCEPT = "Accept"

    /** --- Header Values ---*/
    const val VALUE_JSON = "application/json"
    fun getBearerToken(token: String) = "Bearer $token"
}