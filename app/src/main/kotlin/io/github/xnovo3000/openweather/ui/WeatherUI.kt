package io.github.xnovo3000.openweather.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.github.xnovo3000.openweather.ui.core.*
import io.github.xnovo3000.openweather.ui.route.addlocation.AddLocationScreen
import io.github.xnovo3000.openweather.ui.route.forecast.ForecastScreen
import io.github.xnovo3000.openweather.ui.route.managelocations.ManageLocationsScreen

enum class WeatherRoute(val routeName: String) {
    FORECAST(routeName = "forecast"),
    MANAGE_LOCATIONS(routeName = "manage_locations"),
    FIND_LOCATION(routeName = "find_location"),
    ADD_LOCATION(routeName = "add_location"),
    SETTINGS(routeName = "settings")
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun WeatherUI() {
    // Navigation state
    val navController = rememberAnimatedNavController()
    // Build the UI
    WeatherTheme {
        WeatherSurface {
            AnimatedNavHost(
                navController = navController,
                startDestination = WeatherRoute.FORECAST.routeName,
                enterTransition = EnterTransition,
                exitTransition = ExitTransition,
                popEnterTransition = PopEnterTransition,
                popExitTransition = PopExitTransition
            ) {
                // Forecast
                composable(route = WeatherRoute.FORECAST.routeName) {
                    ForecastScreen(navController = navController)
                }
                // Manage locations
                composable(route = WeatherRoute.MANAGE_LOCATIONS.routeName) {
                    ManageLocationsScreen(navController = navController)
                }
                // Add location
                composable(route = WeatherRoute.ADD_LOCATION.routeName) {
                    AddLocationScreen(navController = navController)
                }
            }
        }
    }
}

fun NavController.navigateWeather(route: WeatherRoute) {
    navigate(route.routeName)
}