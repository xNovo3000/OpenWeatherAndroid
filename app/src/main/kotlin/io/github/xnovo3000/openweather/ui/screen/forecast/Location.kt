package io.github.xnovo3000.openweather.ui.screen.forecast

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
import io.github.xnovo3000.openweather.ui.component.ForecastTopAppBar
import io.github.xnovo3000.openweather.viewmodel.ForecastLocationViewModel

@ExperimentalMaterial3Api
@Composable
fun ForecastScreenLocation(
    onNavigationIconClick: () -> Unit,
    viewModel: ForecastLocationViewModel = hiltViewModel(),
) {
    // Screen state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    // Build screen
    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            // Get location name
            val locationName by viewModel.locationName.collectAsState()
            // Build top bar
            ForecastTopAppBar(
                locationName = locationName,
                onNavigationIconClick = onNavigationIconClick
            )
        }
    ) {
        it
    }
}