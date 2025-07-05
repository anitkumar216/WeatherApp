import SwiftUI
import shared

extension WeatherScreen {
    
    @MainActor
    class ForecastViewModelWrapper: ObservableObject {
        let forecastViewModel: ForecastViewModel
        
        init() {
            forecastViewModel = ForecastInjector().forecastViewModel
            forecastState = forecastViewModel.forecastState.value
        }
        
        @Published var forecastState: ForecastState
        
        func startObserving() {
            Task {
                for await stateF in forecastViewModel.forecastState {
                    self.forecastState = stateF
                }
            }
        }
    }
}

struct WeatherScreen: View {
    
    @ObservedObject private(set) var viewModel: ForecastViewModelWrapper
    
    var body: some View {
        
        VStack {
            if viewModel.forecastState.loading {
                Loader()
            }
            
            if let error = viewModel.forecastState.error {
                ErrorMessage(message: error)
            }
            
            if let forecast = viewModel.forecastState.forecast {
                ForecastScreen(forecast: forecast, onRefresh: { viewModel.forecastViewModel.getForecast(forceRefresh: true) })
            }
        }.onAppear{
            self.viewModel.startObserving()
        }
        
    }
}

struct ForecastScreen: View {
    let forecast: Forecast
    let onRefresh: () -> Void
    
    var body: some View {
        ZStack {
            Color(.systemBackground)
                .ignoresSafeArea()
            
            VStack {
                
                Button(action: {
                    onRefresh()
                }) {
                    Text("Refresh")
                }
                
                // Card container
                ZStack {
                    RoundedRectangle(cornerRadius: 16)
                        .fill(Color(.systemBackground))
                        .shadow(radius: 4)
                    
                    VStack(spacing: 16) {
                        // Location and Time
                        Text(forecast.timezone)
                            .font(.title)
                            .fontWeight(.bold)
                        Text("\(forecast.latitude), \(forecast.longitude) | \(forecast.time)")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                        
                        // Weather Icon and Temperature
                        HStack(spacing: 8) {
                            Text(forecast.weatherIcon)
                                .font(.system(size: 48))
                            VStack(alignment: .leading) {
                                Text(forecast.temperature)
                                    .font(.system(size: 48, weight: .bold))
                                Text(forecast.weather)
                                    .font(.title3)
                                    .foregroundColor(.secondary)
                            }
                        }
                        .padding(.top, 8)
                        
                        // Additional Weather Details
                        HStack(spacing: 16) {
                            WeatherDetailItem(
                                label: "Wind Speed",
                                value: forecast.windSpeed
                            )
                            WeatherDetailItem(
                                label: "Wind Direction",
                                value: forecast.windDirection
                            )
                            WeatherDetailItem(
                                label: "Elevation",
                                value: "\(forecast.elevation) m"
                            )
                        }
                        .padding(.vertical, 8)
                        
                        // Timezone and Day/Night
                        Text("Timezone: \(forecast.timezoneAbbreviation) (\((forecast.isDay == "1") ? "Day" : "Night"))")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                    .padding(24)
                }
                .padding(.horizontal, 16)
                
                Spacer()
            }
            .padding(.top, 16)
        }.refreshable {
            onRefresh() // Trigger the refresh action
        }
    }
}

struct WeatherDetailItem: View {
    let label: String
    let value: String
    
    var body: some View {
        VStack {
            Text(label)
                .font(.caption)
                .foregroundColor(.secondary)
            Text(value)
                .font(.body)
                .fontWeight(.medium)
        }
    }
}

struct Loader: View {
    var body: some View {
        ProgressView()
    }
}

struct ErrorMessage: View {
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}
