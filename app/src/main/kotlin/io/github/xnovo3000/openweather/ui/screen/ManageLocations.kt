package io.github.xnovo3000.openweather.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.ui.WeatherRoute
import io.github.xnovo3000.openweather.ui.component.ManageLocationsFab
import io.github.xnovo3000.openweather.ui.component.ManageLocationsTopBar
import io.github.xnovo3000.openweather.ui.item.ManageLocationsLocation
import io.github.xnovo3000.openweather.ui.navigateWeather
import io.github.xnovo3000.openweather.viewmodel.ManageLocationsViewModel

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
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            ManageLocationsFab(expanded = true) {
                navController.navigateWeather(WeatherRoute.FIND_LOCATIONS)
            }
        }
    ) { paddingValues ->
        // Listen state
        val locations by viewModel.locations.collectAsState()
        // Build sublist
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + WindowInsets.navigationBars
                    .asPaddingValues().calculateBottomPadding()
            )
        ) {
            items(
                items = locations,
                key = { it.id }
            ) {
                ManageLocationsLocation(item = it)
            }
        }
    }
}