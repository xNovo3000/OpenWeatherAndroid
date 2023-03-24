package io.github.xnovo3000.openweather.ui.screen.forecast

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.ui.WeatherRoute
import io.github.xnovo3000.openweather.ui.component.ForecastEmptyContent
import io.github.xnovo3000.openweather.ui.component.ForecastTopAppBar
import io.github.xnovo3000.openweather.ui.navigateWeather

@ExperimentalMaterial3Api
@Composable
fun ForecastScreenEmpty(
    navController: NavController,
    onNavigationIconClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ForecastTopAppBar(
                locationName = null,
                onNavigationIconClick = onNavigationIconClick
            )
        }
    ) { paddingValues ->
        ForecastEmptyContent(
            paddingValues = paddingValues,
            onAddLocationClick = {
                navController.navigateWeather(WeatherRoute.FIND_LOCATIONS)
            }
        )
    }
}