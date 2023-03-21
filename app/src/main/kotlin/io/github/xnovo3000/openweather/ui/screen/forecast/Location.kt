package io.github.xnovo3000.openweather.ui.screen.forecast

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.viewmodel.ForecastLocationViewModel

@ExperimentalMaterial3Api
@Composable
fun ForecastScreenLocation(
    navController: NavController,
    viewModel: ForecastLocationViewModel = hiltViewModel()
) {
    // Listen for data
    // Build screen
    Scaffold {
        it
    }
}