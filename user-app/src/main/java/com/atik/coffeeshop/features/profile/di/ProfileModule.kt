package com.atik.coffeeshop.features.profile.di

import com.atik.coffeeshop.features.profile.data.api.ProfileApiService
import com.atik.coffeeshop.features.profile.data.repositories.ProfileRepositoryImpl
import com.atik.coffeeshop.features.profile.domain.repository.ProfileRepository
import com.atik.coffeeshop.features.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {
    single { get<Retrofit>().create(ProfileApiService::class.java) }
    single<ProfileRepository> { ProfileRepositoryImpl(api = get(), json = get()) }
    viewModel { ProfileViewModel(userPreferences = get(), profileRepository = get()) }

}