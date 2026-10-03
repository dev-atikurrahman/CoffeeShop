package com.atik.coffeeshop.di

import android.app.Application
import com.atik.coffeeshop.shared.di.preferencesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoffeeShopApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CoffeeShopApplication)
            modules(
                preferencesModule,
                networkModule,
                appModule
            )
        }
    }
}