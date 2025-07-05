package com.agrichain.weatherapp.forecast

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class WeatherAppService(private val httpClient: HttpClient) {

    suspend fun fetchForecast(): ForecastResponse {
        val response: ForecastResponse = httpClient
            .get("https://api.open-meteo.com/v1/forecast?latitude=40.71&longitude=-74.01&current_weather=true")
            .body()

        return response
    }

}