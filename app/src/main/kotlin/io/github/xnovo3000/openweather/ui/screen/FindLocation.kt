package io.github.xnovo3000.openweather.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.ui.component.FindLocationTopBar
import io.github.xnovo3000.openweather.ui.item.CurrentLocation
import io.github.xnovo3000.openweather.ui.item.GeocodedLocation
import io.github.xnovo3000.openweather.ui.item.GeocodedLocationItem
import io.github.xnovo3000.openweather.viewmodel.FindLocationViewModel

@ExperimentalMaterial3Api
@Composable
fun FindLocationScreen(
    navController: NavController,
    viewModel: FindLocationViewModel = hiltViewModel()
) {
    // Screen state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    // Build screen
    Scaffold(
        topBar = {
            // Listen query
            var query by remember { mutableStateOf("") }
            // Update VM
            LaunchedEffect(query) { viewModel.updateQuery(query) }
            // Build top bar
            FindLocationTopBar(
                query = query,
                onQueryChange = { query = it },
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        // Listen state
        val geocodedLocations by viewModel.geocodedLocationItems.collectAsStateWithLifecycle()
        val isCurrentLocationAlreadyPresent by viewModel.isCurrentLocationAlreadyPresent.collectAsStateWithLifecycle()
        val addCurrentLocationCounter = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                viewModel.insertCurrentLocation()
            } else {
                // TODO: Add snackbar to indicate that permission is required
            }
        }
        // Build
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding() + WindowInsets.navigationBars
                    .asPaddingValues().calculateBottomPadding()
            )
        ) {
            // Build current location
            item(key = 0) {
                CurrentLocation(isAlreadyPresent = isCurrentLocationAlreadyPresent) {
                    addCurrentLocationCounter.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            }
            // Build locations
            items(
                items = geocodedLocations,
                key = { it.id }
            ) {
                GeocodedLocation(item = it) {
                    viewModel.insertLocation(it)
                }
            }
        }
    }
}