package io.github.xnovo3000.openweather.ui.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.github.xnovo3000.openweather.ui.component.ForecastDrawer
import io.github.xnovo3000.openweather.viewmodel.ForecastViewModel

sealed class ForecastScreenRoute(val routeName: String) {
    object Empty : ForecastScreenRoute(routeName = "empty")
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
    // Build the screen
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Get selected locations
            val locations by viewModel.locations.collectAsState()
            // Build drawer with locations
            ForecastDrawer(
                selectedLocationId = 0,
                items = locations,
                onItemClick = {},
                onManageLocationsClick = {},
                onSettingsClick = {}
            )
        }
    ) {
        AnimatedNavHost(
            navController = innerNavController,
            startDestination = ForecastScreenRoute.Empty.routeName
        ) {

        }
    }
}