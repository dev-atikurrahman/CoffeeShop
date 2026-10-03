package com.atik.coffeeshop.features.auth.data.repositories

import android.util.Log
import com.atik.coffeeshop.core.APIResponse
import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import com.atik.coffeeshop.features.auth.data.models.UserDto
import com.atik.coffeeshop.features.auth.domain.models.User
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val authApi: AuthApiService
) : AuthRepository {
    companion object {
        private const val TAG = "AuthDebug"
    }

    override suspend fun register(
        request: UserDto
    ): Result<APIResponse<User>> = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "Registration Start: ${request.email}")
            val response = authApi.register(request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Log.d(TAG, "Registration success: ${body.success}")
                    Result.success(body)
                } else {
                    Log.d(TAG, "Response body is null!")
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Log.d(TAG, "HTTP ${response.code()} ${response.message()}")
                Result.failure(HttpException(response))
            }

        } catch (e: HttpException) {
            Log.d(TAG, "Http error: ${e.code()}", e)
            Result.failure(e)
        } catch (e: IOException) {
            Log.d(TAG, "Network error", e)
            Result.failure(Exception("Network error. Check your connection."))
        } catch (e: Exception) {
            Log.d(TAG, "Unexpected error", e)
            Result.failure(e)
        }
    }


}