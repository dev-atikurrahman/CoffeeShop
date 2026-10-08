package com.atik.coffeeshop.core.network

class ApiException(
    override val message: String,
    val statusCode: Int? = null,
    val fieldErrors: Map<String, List<String>> = emptyMap()
) : Exception(message)