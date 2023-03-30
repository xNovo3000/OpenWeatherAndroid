package io.github.xnovo3000.openweather.ui.route.forecast

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.ui.WeatherRoute
import io.github.xnovo3000.openweather.ui.core.withNavigationBarPadding
import io.github.xnovo3000.openweather.ui.navigateWeather
import io.github.xnovo3000.openweather.viewmodel.Forecast2ViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun ForecastScreen(
    navController: NavController,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    // Screen state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    // Build screen
    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            ForecastTopBar(
                onSearchClick = {
                    navController.navigateWeather(WeatherRoute.FIND_LOCATION)
                },
                onManageLocationsClick = {
                    navController.navigateWeather(WeatherRoute.MANAGE_LOCATIONS)
                },
                onSettingsClick = {
                    navController.navigateWeather(WeatherRoute.SETTINGS)
                },
                onInfoClick = {
                    // TODO: Show info
                }
            )
        }
    ) { paddingValues ->
        // TODO: Get state
        // Build content
        ForecastContent(
            locations = emptyList(),
            onLocationRefresh = {},
            paddingValues = paddingValues.withNavigationBarPadding
        )
    }
}