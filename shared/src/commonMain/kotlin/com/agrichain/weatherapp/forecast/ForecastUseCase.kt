package com.agrichain.weatherapp.forecast

class ForecastUseCase(private val repository: ForecastRepository) {

    suspend fun getForecast(forceRefresh: Boolean) : Forecast {
        val forecast = repository.getForecast(forceRefresh)
        return forecast
    }
}