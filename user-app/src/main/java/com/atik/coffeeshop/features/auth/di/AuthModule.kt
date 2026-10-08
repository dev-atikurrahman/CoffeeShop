package com.atik.coffeeshop.features.auth.di

import com.atik.coffeeshop.features.auth.data.api.AuthApiService
import com.atik.coffeeshop.features.auth.data.repositories.AuthRepositoryImpl
import com.atik.coffeeshop.features.auth.domain.repository.AuthRepository
import com.atik.coffeeshop.features.auth.presentation.login.LoginViewModel
import com.atik.coffeeshop.features.auth.presentation.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(AuthApiService::class.java) }
    single<AuthRepository> { AuthRepositoryImpl(api = get(), json = get()) }
    viewModel { LoginViewModel(authRepository = get(), userPreferences = get()) }
    viewModel { RegisterViewModel(authRepository = get(), userPreferences = get()) }
}