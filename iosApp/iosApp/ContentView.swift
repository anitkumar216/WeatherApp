import SwiftUI
import shared

struct ContentView: View {

	var body: some View {
        WeatherScreen(viewModel: .init())
    }
}
