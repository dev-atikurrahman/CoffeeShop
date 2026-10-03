package com.atik.coffeeshop.data.remote.api

import android.content.Context
import com.atik.coffeeshop.data.remote.api.ApiConstants.BASE_URL
import com.atik.coffeeshop.data.remote.api.ApiConstants.TIMEOUT_CONNECT
import com.atik.coffeeshop.data.remote.api.ApiConstants.TIMEOUT_READ
import com.atik.coffeeshop.data.remote.api.ApiConstants.TIMEOUT_WRITE
import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    @Volatile
    private var instance: Retrofit? = null

    fun getInstance(context: Context): Retrofit {
        if (instance == null) {
            instance ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .also { instance = it }
        }
        return instance!!
    }

    fun getAuthApiService(context: Context): AuthApiService {
        return getInstance(context).create(AuthApiService::class.java)
    }

    private fun getOkHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .retryOnConnectionFailure(true)
            .build()
    }
}