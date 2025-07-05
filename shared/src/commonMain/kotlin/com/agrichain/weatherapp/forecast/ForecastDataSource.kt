package com.agrichain.weatherapp.forecast

import com.agrichain.weatherapp.db.WeatherDatabase

class ForecastDataSource(private val database: WeatherDatabase) {

    fun getForecast(): Forecast? =
        database.weatherDatabaseQueries.selectForecast(::mapToForecast).executeAsOneOrNull()

    private fun mapToForecast(
        latitude: String?,
        longitude: String?,
        generationTimeMs: String?,
        utcOffsetSeconds: String?,
        timeZone: String?,
        timeZoneAbbreviation: String?,
        elevation: String?,
        time: String?,
        interval: String?,
        temperature: String?,
        windSpeed: String?,
        windDirection: String?,
        isDay: String?,
        weather: String?,
        weatherIcon: String?
    ) : Forecast =
        Forecast(
            latitude = latitude ?: "",
            longitude = longitude ?: "",
            generationTimeMs = generationTimeMs ?: "",
            utcOffsetSeconds = utcOffsetSeconds ?: "",
            timezone = timeZone ?: "",
            timezoneAbbreviation = timeZoneAbbreviation ?: "",
            elevation = elevation ?: "",
            time = time ?: "",
            interval = interval ?: "",
            temperature = temperature ?: "",
            windSpeed = windSpeed ?: "",
            windDirection = windDirection ?: "",
            isDay = isDay ?: "",
            weather = weather ?: "",
            weatherIcon = weatherIcon ?: "",
        )

    fun insertForecast(forecast: Forecast) {
        database.weatherDatabaseQueries.transaction {
            database.weatherDatabaseQueries.insertForecast(
                forecast.latitude,
                forecast.longitude,
                forecast.generationTimeMs,
                forecast.utcOffsetSeconds,
                forecast.timezone,
                forecast.timezoneAbbreviation,
                forecast.elevation,
                forecast.time,
                forecast.interval,
                forecast.temperature,
                forecast.windSpeed,
                forecast.windDirection,
                forecast.isDay,
                forecast.weather,
                forecast.weatherIcon
            )
        }
    }

    fun removeForecast() {
        database.weatherDatabaseQueries.removeForecast()
    }

}