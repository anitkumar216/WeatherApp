package com.agrichain.weatherapp.di

import com.agrichain.weatherapp.forecast.ForecastViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    val module = sharedKoinModules + databaseModule

    startKoin {
        modules(module)
    }
}

class ForecastInjector : KoinComponent {
    val forecastViewModel: ForecastViewModel by inject()
}