package com.atik.coffeeshop.data.remote.api

object ApiConstants {
    /** --- Base URLs ---*/
    const val BASE_URL = ""

    /** --- Network Configurations ---*/
    const val TIMEOUT_CONNECT = 30L
    const val TIMEOUT_READ = 30L
    const val TIMEOUT_WRITE = 30L

    /** --- API Endpoints ---*/
    const val ENDPOINT_REGISTRATION = "users/auth/register.php"

    /** --- Header Keys ---*/
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_CONTENT_TYPE = "Content-Type"
    const val HEADER_ACCEPT = "Accept"

    /** --- Header Values ---*/
    const val VALUE_JSON = "application/json"
    fun getBearerToken(token: String) = "Bearer $token"
}