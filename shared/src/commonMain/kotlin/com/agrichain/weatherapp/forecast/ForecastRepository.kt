package com.agrichain.weatherapp.forecast

class ForecastRepository(
    private val dataSource: ForecastDataSource,
    private val weatherAppService: WeatherAppService
) {

    suspend fun getForecast(forceRefresh: Boolean) : Forecast {

        if (forceRefresh) {
            dataSource.removeForecast()
            return fetchForecast()
        }

        val forecast = dataSource.getForecast()

        if (forecast == null) {
            return fetchForecast()
        }

        return forecast
    }

    private suspend fun fetchForecast(): Forecast {
        val fetchForecast = weatherAppService.fetchForecast()
        val forecast = mapToForecast(fetchForecast)
        dataSource.insertForecast(forecast)
        return forecast
    }

    fun mapToForecast(response: ForecastResponse): Forecast = response.let {
        Forecast(
            latitude = it.latitude.toString(),
            longitude = it.longitude.toString(),
            generationTimeMs = it.generationTimeMs.toString(),
            utcOffsetSeconds = it.utcOffsetSeconds.toString(),
            timezone = it.timeZone.toString(),
            timezoneAbbreviation = it.timeZoneAbbreviation.toString(),
            elevation = it.elevation.toString(),
            time = "${it.currentWeather?.time} ${it.currentWeatherUnits?.time}",
            interval = "${it.currentWeather?.interval} ${it.currentWeatherUnits?.interval}",
            temperature = "${it.currentWeather?.temperature} ${it.currentWeatherUnits?.temperature}",
            windSpeed = "${it.currentWeather?.windSpeed} ${it.currentWeatherUnits?.windSpeed}",
            windDirection = "${it.currentWeather?.windDirection} ${it.currentWeatherUnits?.windDirection}",
            isDay = isDay(it.currentWeather?.isDay ?: 0).toString(),
            weather = weatherCondition(it.currentWeather?.weatherCode ?: 0),
            weatherIcon = weatherIcon(it.currentWeather?.isDay ?: 0, it.currentWeather?.weatherCode ?: 0)
        )
    }

    private fun weatherCondition(weatherCode: Int): String {
        return when (weatherCode) {
            0 -> "Clear Sky"
            1 -> "Partly Cloudy"
            else -> "Unknown"
        }
    }

    private fun isDay(isDay: Int): Boolean {
        return (isDay == 1)
    }

    private fun weatherIcon(isDay: Int, weatherCode: Int): String {
        return if (isDay(isDay)) {
            when (weatherCode) {
                0 -> "☀️" // Sun icon for clear sky
                1 -> "⛅" // Sun with cloud for partly cloudy
                else -> "❓"
            }
        } else {
            when (weatherCode) {
                0 -> "🌙" // Moon icon for clear sky
                1 -> "☁️" // Cloud with moon for partly cloudy
                else -> "❓"
            }
        }
    }

}