package com.agrichain.weatherapp.forecast

data class ForecastState(
    val forecast: Forecast? = null,
    val loading: Boolean = false,
    val error: String? = null
)