package io.github.xnovo3000.openweather.ui.route.managelocations

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.ui.WeatherRoute
import io.github.xnovo3000.openweather.ui.navigateWeather

@ExperimentalMaterial3Api
@Composable
fun ManageLocationsScreen(
    navController: NavController,
    viewModel: ManageLocationsViewModel = hiltViewModel()
) {
    // Screen state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    // Build screen
    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            ManageLocationsTopBar(
                scrollBehavior = scrollBehavior,
                onNavigationIconClick = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            ManageLocationsFab(expanded = true) {
                navController.navigateWeather(WeatherRoute.ADD_LOCATION)
            }
        }
    ) { paddingValues ->
        // Listen state
        val locations by viewModel.locations.collectAsStateWithLifecycle()
        // Build
        ManageLocationsContent(
            locations = locations,
            paddingValues = paddingValues
        )
    }
}