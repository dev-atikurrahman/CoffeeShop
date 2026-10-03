package com.atik.coffeeshop.core

sealed class APIResponse<out T> {
    data object Idle : APIResponse<Nothing>()
    data object Loading : APIResponse<Nothing>()
    data class Success<out T>(val data: T) : APIResponse<T>()
    data class Error(
        val message: String,
        val exception: Throwable? = null
    ) : APIResponse<Nothing>()
}