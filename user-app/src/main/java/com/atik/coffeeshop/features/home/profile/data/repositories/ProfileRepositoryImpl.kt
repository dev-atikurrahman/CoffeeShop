package com.atik.coffeeshop.features.home.profile.data.repositories

import com.atik.coffeeshop.core.ApiException
import com.atik.coffeeshop.features.auth.data.models.ApiEnvelope
import com.atik.coffeeshop.features.auth.data.models.ErrorBodyDto
import com.atik.coffeeshop.features.auth.domain.models.User
import com.atik.coffeeshop.features.auth.domain.models.toDomain
import com.atik.coffeeshop.features.home.profile.data.api.ProfileApiService
import com.atik.coffeeshop.features.home.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl(
    private val api: ProfileApiService,
    private val json: Json
) : ProfileRepository {

    override suspend fun getProfile(): Result<User> =
        safeApiCall(json) { api.getMe() }.map { it.user.toDomain() }

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
            Result.failure(ApiException("Network error. Check your connection."))
        } catch (e: SerializationException) {
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
            message = body?.message?.takeIf { it.isNotBlank() }
                ?: "Request failed (${response.code()})",
            statusCode = response.code(),
            fieldErrors = body?.errors.orEmpty()
        )
    }
}