package com.atik.coffeeshop.di

import com.atik.coffeeshop.data.remote.api.ApiConstants
import com.atik.coffeeshop.data.remote.interceptor.AuthInterceptor
import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import com.atik.coffeeshop.features.auth.data.repositories.AuthRepositoryImpl
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
import com.atik.coffeeshop.features.home.profile.data.api.ProfileApiService
import com.atik.coffeeshop.features.home.profile.data.repositories.ProfileRepositoryImpl
import com.atik.coffeeshop.features.home.profile.domain.repository.ProfileRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
            coerceInputValues = true
        }
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(ApiConstants.TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(ApiConstants.TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(ApiConstants.TIMEOUT_WRITE, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(AuthInterceptor(get()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single { get<Retrofit>().create(AuthApiService::class.java) }
    single { get<Retrofit>().create(ProfileApiService::class.java) }
    single<AuthRepository> { AuthRepositoryImpl(api = get(), json = get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(api = get(), json = get()) }
}