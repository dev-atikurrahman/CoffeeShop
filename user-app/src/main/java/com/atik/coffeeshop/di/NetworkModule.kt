package com.atik.coffeeshop.di

import com.atik.coffeeshop.data.remote.api.ApiConstants
import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import com.atik.coffeeshop.features.auth.data.repositories.AuthRepositoryImpl
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
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
            // BODY লেভেল দেবেন না, লগে পাসওয়ার্ড দেখা যাবে
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
    single<AuthRepository> { AuthRepositoryImpl(api = get(), json = get()) }
}