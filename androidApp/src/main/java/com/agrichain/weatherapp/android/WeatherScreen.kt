package com.agrichain.weatherapp.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agrichain.weatherapp.forecast.Forecast
import com.agrichain.weatherapp.forecast.ForecastViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(
    forecastViewModel: ForecastViewModel = koinViewModel()
) {

    val forecastState = forecastViewModel.forecastState.collectAsState()

    if (forecastState.value.loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (forecastState.value.error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = forecastState.value.error!!
            )
        }
    }

    if (forecastState.value.forecast != null) {
        ForecastScreen(
            forecast = forecastState.value.forecast!!,
            onRefresh = {
                forecastViewModel.getForecast(true)
            }
        )
    }


}

@Composable
fun ForecastScreen(
    forecast: Forecast,
    onRefresh: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        Button(
                            onClick = {
                                onRefresh()
                            },
                        ) {
                            Text(
                                text = "Refresh"
                            )
                        }

                        // Location and Time
                        Text(
                            text = forecast.timezone,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${forecast.latitude}, ${forecast.longitude} | ${forecast.time}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Weather Icon and Temperature
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = forecast.weatherIcon,
                                fontSize = 48.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column {
                                Text(
                                    text = forecast.temperature,
                                    style = MaterialTheme.typography.displayMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = forecast.weather,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Additional Weather Details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            WeatherDetailItem(
                                label = "Wind Speed",
                                value = forecast.windSpeed
                            )
                            WeatherDetailItem(
                                label = "Wind Direction",
                                value = forecast.windDirection
                            )
                            WeatherDetailItem(
                                label = "Elevation",
                                value = "${forecast.elevation} m"
                            )
                        }

                        // Timezone and Day/Night
                        Text(
                            text = "Timezone: ${forecast.timezoneAbbreviation} (${if (forecast.isDay.toBoolean()) "Day" else "Night"})",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}