package com.agrichain.weatherapp.android

import android.app.Application
import com.agrichain.weatherapp.android.di.databaseModule
import com.agrichain.weatherapp.android.di.viewModelsModule
import com.agrichain.weatherapp.di.sharedKoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKoinModules + viewModelsModule + databaseModule

        startKoin {
            androidContext(this@WeatherApp)
            modules(modules)
        }
    }
}