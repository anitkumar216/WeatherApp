package com.agrichain.weatherapp.forecast

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ForecastResponse(
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("generationtime_ms")
    val generationTimeMs: Double?,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Int?,
    @SerialName("timezone")
    val timeZone: String?,
    @SerialName("timezone_abbreviation")
    val timeZoneAbbreviation: String?,
    @SerialName("elevation")
    val elevation: Double?,
    @SerialName("current_weather_units")
    val currentWeatherUnits: CurrentWeatherUnitsRaw?,
    @SerialName("current_weather")
    val currentWeather: CurrentWeatherRaw?
)

@Serializable
data class CurrentWeatherUnitsRaw(
    @SerialName("time")
    val time: String?,
    @SerialName("interval")
    val interval: String?,
    @SerialName("temperature")
    val temperature: String?,
    @SerialName("windspeed")
    val windSpeed: String?,
    @SerialName("winddirection")
    val windDirection: String?,
    @SerialName("is_day")
    val isDay: String?,
    @SerialName("weathercode")
    val weatherCode: String?
)

@Serializable
data class CurrentWeatherRaw(
    @SerialName("time")
    val time: String?,
    @SerialName("interval")
    val interval: Int?,
    @SerialName("temperature")
    val temperature: Double?,
    @SerialName("windspeed")
    val windSpeed: Double?,
    @SerialName("winddirection")
    val windDirection: Int?,
    @SerialName("is_day")
    val isDay: Int?,
    @SerialName("weathercode")
    val weatherCode: Int?
)