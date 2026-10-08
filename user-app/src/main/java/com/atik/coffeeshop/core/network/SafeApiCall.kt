package com.atik.coffeeshop.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> safeApiCall(
    json: Json,
    call: suspend () -> Response<ApiEnvelope<T>>
): Result<T> = withContext(Dispatchers.IO) {
    try {
        val response = call()
        if (response.isSuccessful) {
            val data = response.body()?.data
            if (data != null) Result.success(data)
            else Result.failure(ApiException("Empty response from server", response.code()))
        } else {
            Result.failure(parseError(json, response))
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: IOException) {
        val msg = when (e) {
            is InterruptedIOException -> "Request timed out. Please try again."
            is ConnectException, is UnknownHostException ->
                "Can't reach the server. Check your connectioin and try again."

            else -> "Network error. Check your connection."
        }
        Result.failure(ApiException(msg))    } catch (e: SerializationException) {
        Result.failure(ApiException("Unexpected server response."))
    } catch (e: Exception) {
        Result.failure(ApiException(e.message ?: "Something went wrong"))
    }
}

private fun parseError(json: Json, response: Response<*>): ApiException {
    val body = runCatching {
        json.decodeFromString<ErrorBodyDto>(response.errorBody()?.string().orEmpty())
    }.getOrNull()

    return ApiException(
        message = body?.message?.takeIf { it.isNotBlank() } ?: "Request failed (${response.code()})",
        statusCode = response.code(),
        fieldErrors = body?.errors.orEmpty()
    )
}