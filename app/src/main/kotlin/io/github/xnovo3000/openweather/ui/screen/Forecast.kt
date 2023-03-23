package io.github.xnovo3000.openweather.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.github.xnovo3000.openweather.ui.WeatherRoute
import io.github.xnovo3000.openweather.ui.component.ForecastDrawer
import io.github.xnovo3000.openweather.ui.navigate
import io.github.xnovo3000.openweather.ui.screen.forecast.ForecastScreenEmpty
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
            // TODO: Update innerNavController in case of invalid route
            LaunchedEffect(locations, backStackEntry) {

            }
            // Build drawer with locations
            ForecastDrawer(
                selectedLocationId = 0,
                items = locations,
                onItemClick = {
                    innerNavController.navigate(
                        route = ForecastScreenRoute.LOCATIONS,
                        args = mapOf("id" to it.id)
                    )
                },
                onManageLocationsClick = {
                    navController.navigate(WeatherRoute.MANAGE_LOCATIONS)
                },
                onSettingsClick = {
                    navController.navigate(WeatherRoute.SETTINGS)
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

            }
        }
    }
}

fun NavController.navigate(route: ForecastScreenRoute, args: Map<String, Any>) {
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