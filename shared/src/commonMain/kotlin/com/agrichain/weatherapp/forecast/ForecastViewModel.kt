package com.agrichain.weatherapp.forecast

import com.agrichain.weatherapp.BaseViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class ForecastViewModel(
    private val useCase: ForecastUseCase
): BaseViewModel() {

    private val _forecastState: MutableStateFlow<ForecastState> = MutableStateFlow(
        ForecastState(
            loading = true
        )
    )
    val forecastState: StateFlow<ForecastState> = _forecastState

    init {
        getForecast()
    }

    fun getForecast(forceRefresh: Boolean = false) {
        scope.launch {

            val fetchedForecast = useCase.getForecast(forceRefresh)

            _forecastState.emit(ForecastState(
                forecast = fetchedForecast
            ))
        }
    }

}