package com.agrichain.weatherapp.forecast

data class Forecast(
    val latitude: String,
    val longitude: String,
    val generationTimeMs: String,
    val utcOffsetSeconds: String,
    val timezone: String,
    val timezoneAbbreviation: String,
    val elevation: String,
    val time: String,
    val interval: String,
    val temperature: String,
    val windSpeed: String,
    val windDirection: String,
    val isDay: String,
    val weather: String,
    val weatherIcon: String
)