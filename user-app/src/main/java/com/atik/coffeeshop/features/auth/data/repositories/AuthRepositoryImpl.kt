package com.atik.coffeeshop.features.auth.data.repositories

import com.atik.coffeeshop.core.ApiException
import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import com.atik.coffeeshop.features.auth.data.models.ApiEnvelope
import com.atik.coffeeshop.features.auth.data.models.AuthDataDto
import com.atik.coffeeshop.features.auth.data.models.ErrorBodyDto
import com.atik.coffeeshop.features.auth.data.models.LoginRequestDto
import com.atik.coffeeshop.features.auth.data.models.RegisterRequestDto
import com.atik.coffeeshop.features.auth.domain.models.AuthSession
import com.atik.coffeeshop.features.auth.domain.models.toDomain
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
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

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val json: Json
) : AuthRepository {

    companion object {
        private const val TAG = "AuthDebug"
    }

    override suspend fun register(name: String, email: String, password: String) =
        safeAuthCall { api.register(RegisterRequestDto(name.trim(), email.trim(), password)) }


    override suspend fun login(email: String, password: String) =
        safeAuthCall { api.login(LoginRequestDto(email.trim(), password)) }

    private suspend fun safeAuthCall(
        call: suspend () -> Response<ApiEnvelope<AuthDataDto>>
    ): Result<AuthSession> = withContext(Dispatchers.IO) {
        try {
            val response = call()
            if (response.isSuccessful) {
                val data = response.body()?.data
                if (data != null) {
                    Result.success(AuthSession(data.user.toDomain(), data.token))
                } else {
                    Result.failure(ApiException("Empty response from server", response.code()))
                }
            } else {
                Result.failure(parseError(response))
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
            Result.failure(ApiException(msg))
        } catch (e: SerializationException) {
            Result.failure(ApiException("Unexpected server response."))
        } catch (e: Exception) {
            Result.failure(ApiException(e.message ?: "Something went wrong"))
        }
    }

    private fun parseError(response: Response<*>): ApiException {
        val body = runCatching {
            json.decodeFromString<ErrorBodyDto>(response.errorBody()?.string().orEmpty())
        }.getOrNull()

        return ApiException(message = body?.message?.takeIf { it.isNotBlank() }
            ?: "Request failed (${response.code()})",
            statusCode = response.code(),
            fieldErrors = body?.errors.orEmpty())
    }

}











