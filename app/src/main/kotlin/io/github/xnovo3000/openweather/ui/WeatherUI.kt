package io.github.xnovo3000.openweather.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.github.xnovo3000.openweather.ui.screen.ForecastScreen
import io.github.xnovo3000.openweather.ui.theme.WeatherSurface
import io.github.xnovo3000.openweather.ui.theme.WeatherTheme

enum class WeatherRoute(val routeName: String) {
    FORECAST(routeName = "forecast"),
    MANAGE_LOCATIONS(routeName = "manage_locations"),
    FIND_LOCATIONS(routeName = "add_locations"),
    SETTINGS(routeName = "settings")
}

@ExperimentalAnimationApi
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
                startDestination = WeatherRoute.FORECAST.routeName
            ) {
                // Forecast
                composable(route = WeatherRoute.FORECAST.routeName) {
                    ForecastScreen(navController = navController)
                }
            }
        }
    }
}

fun NavController.navigate(route: WeatherRoute) {
    navigate(route.routeName)
}