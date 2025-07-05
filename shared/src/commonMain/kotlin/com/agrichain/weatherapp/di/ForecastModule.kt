package com.agrichain.weatherapp.di

import com.agrichain.weatherapp.forecast.ForecastDataSource
import com.agrichain.weatherapp.forecast.ForecastRepository
import com.agrichain.weatherapp.forecast.ForecastUseCase
import com.agrichain.weatherapp.forecast.ForecastViewModel
import com.agrichain.weatherapp.forecast.WeatherAppService
import org.koin.dsl.module

val forecastModule = module {

    single<WeatherAppService> { WeatherAppService(get()) }
    single<ForecastUseCase> { ForecastUseCase(get()) }
    single<ForecastViewModel> { ForecastViewModel(get()) }
    single<ForecastDataSource> { ForecastDataSource(get()) }
    single<ForecastRepository> { ForecastRepository(get(), get()) }

}