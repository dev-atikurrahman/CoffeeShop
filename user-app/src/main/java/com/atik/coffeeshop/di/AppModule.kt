package com.atik.coffeeshop.di

import com.atik.coffeeshop.features.details.presentation.DetailsViewModel
import com.atik.coffeeshop.features.auth.presentation.login.LoginViewModel
import com.atik.coffeeshop.features.auth.presentation.register.RegisterViewModel
import com.atik.coffeeshop.features.home.explore.presentation.SharedViewModel
import com.atik.coffeeshop.features.home.profile.presentation.ProfileViewModel
import com.atik.coffeeshop.navigation.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RegisterViewModel(authRepository = get(), userPreferences = get()) }
    viewModel { LoginViewModel(authRepository = get(), userPreferences = get()) }
    viewModel { SplashViewModel(userPreferences = get()) }
    viewModel { SharedViewModel() }
    viewModel { ProfileViewModel(userPreferences = get(), profileRepository = get()) }
    viewModel { DetailsViewModel() }
}