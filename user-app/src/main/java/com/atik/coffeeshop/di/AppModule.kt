package com.atik.coffeeshop.di

import com.atik.coffeeshop.features.explore.presentation.SharedViewModel
import com.atik.coffeeshop.navigation.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel(userPreferences = get()) }
    viewModel { SharedViewModel() }
}