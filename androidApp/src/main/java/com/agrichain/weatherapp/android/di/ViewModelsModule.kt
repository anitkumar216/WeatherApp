package com.agrichain.weatherapp.android.di


import com.agrichain.weatherapp.forecast.ForecastViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ForecastViewModel(get()) }
}