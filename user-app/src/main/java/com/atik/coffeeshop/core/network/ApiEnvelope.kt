package com.atik.coffeeshop.core.network

import kotlinx.serialization.Serializable

@Serializable
data class ApiEnvelope<T>(
    val success: Boolean,
    val message: String = "",
    val data: T? = null,
    val errors: Map<String, List<String>>? = null
)

@Serializable
data class ErrorBodyDto(
    val message: String = "",
    val errors: Map<String, List<String>>? = null
)