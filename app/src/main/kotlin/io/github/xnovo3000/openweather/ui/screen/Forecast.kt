package io.github.xnovo3000.openweather.ui.screen

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.github.xnovo3000.openweather.ui.WeatherRoute
import io.github.xnovo3000.openweather.ui.component.ForecastDrawer
import io.github.xnovo3000.openweather.ui.navigateWeather
import io.github.xnovo3000.openweather.ui.screen.forecast.ForecastScreenEmpty
import io.github.xnovo3000.openweather.ui.screen.forecast.ForecastScreenLocation
import io.github.xnovo3000.openweather.viewmodel.ForecastViewModel
import kotlinx.coroutines.launch

enum class ForecastScreenRoute(val routeName: String) {
    EMPTY(routeName = "empty"),
    LOCATIONS(routeName = "location/{id}")
}

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun ForecastScreen(
    navController: NavController,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    // Inner navigation
    val innerNavController = rememberAnimatedNavController()
    // State management
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    // Build the screen
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Get selected locations
            val locations by viewModel.locations.collectAsState()
            val backStackEntry by innerNavController.currentBackStackEntryAsState()
            // Update innerNavController in case of invalid route
            LaunchedEffect(locations, backStackEntry) {
                // Get the ID of the selected location and location ids
                val destination = backStackEntry?.destination?.route ?: ForecastScreenRoute.EMPTY.routeName
                val id = backStackEntry?.arguments?.getLong("id", -1L)
                val locationIds = locations.map { it.id }
                // Manage navigation when invalid
                // 1: locations empty and destination not empty -> go to empty
                // TODO: CHECK: 2: locations not empty but id not in list
                when {
                    locationIds.isEmpty() && destination != ForecastScreenRoute.EMPTY.routeName -> {
                        innerNavController.navigateForecast(ForecastScreenRoute.EMPTY)
                    }
                    locations.isNotEmpty() && !locationIds.contains(id) -> {
                        innerNavController.navigateForecast(
                            route = ForecastScreenRoute.LOCATIONS,
                            args = mapOf("id" to locationIds.first())
                        )
                    }
                }
            }
            // Build drawer with locations
            ForecastDrawer(
                selectedLocationId = backStackEntry?.arguments?.getLong("id") ?: -1L,
                items = locations,
                onItemClick = {
                    innerNavController.navigateForecast(
                        route = ForecastScreenRoute.LOCATIONS,
                        args = mapOf("id" to it.id)
                    )
                },
                onManageLocationsClick = {
                    navController.navigateWeather(WeatherRoute.MANAGE_LOCATIONS)
                },
                onSettingsClick = {
                    navController.navigateWeather(WeatherRoute.SETTINGS)
                }
            )
        }
    ) {
        AnimatedNavHost(
            navController = innerNavController,
            startDestination = ForecastScreenRoute.EMPTY.routeName,
            route = "forecast"
        ) {
            // Empty route
            composable(route = ForecastScreenRoute.EMPTY.routeName) {
                ForecastScreenEmpty(
                    navController = navController,
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
            // Route with a location -> show forecast
            composable(route = ForecastScreenRoute.LOCATIONS.routeName) {
                ForecastScreenLocation(
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
        }
    }
}

fun NavController.navigateForecast(route: ForecastScreenRoute, args: Map<String, Any> = emptyMap()) {
    when (route) {
        ForecastScreenRoute.LOCATIONS -> {
            navigate(route.routeName.replace("id", "${args["id"]}")) {
                popUpTo(graph.id) { inclusive = true }
            }
        }
        else -> {
            navigate(route.routeName) {
                popUpTo(graph.id) { inclusive = true }
            }
        }
    }
}