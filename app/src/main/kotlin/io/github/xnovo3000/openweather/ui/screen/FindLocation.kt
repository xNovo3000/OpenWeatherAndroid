package io.github.xnovo3000.openweather.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.component.FindLocationContent
import io.github.xnovo3000.openweather.ui.component.FindLocationTopBar
import io.github.xnovo3000.openweather.viewmodel.FindLocationViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun FindLocationScreen(
    navController: NavController,
    viewModel: FindLocationViewModel = hiltViewModel()
) {
    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    // TopBar scroll state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    // Build screen
    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            // Listen query
            var query by remember { mutableStateOf("") }
            // Update VM
            LaunchedEffect(query) { viewModel.updateQuery(query) }
            // Build top bar
            FindLocationTopBar(
                query = query,
                onQueryChange = { query = it },
                onNavigationIconClick = { navController.popBackStack() },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        // Get values
        val permissionRequiredRationale = stringResource(id = R.string.find_location_permission_required)
        // Listen state
        val geocodedLocations by viewModel.geocodedLocationItems.collectAsStateWithLifecycle()
        val isCurrentLocationAlreadyPresent by viewModel.isCurrentLocationAlreadyPresent.collectAsStateWithLifecycle()
        val addCurrentLocationLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { result ->
            if (result) {
                viewModel.insertCurrentLocation()
            } else {
                coroutineScope.launch { snackbarHostState.showSnackbar(permissionRequiredRationale) }
            }
        }
        // Build
        FindLocationContent(
            geocodedLocations = geocodedLocations,
            isCurrentLocationAlreadyPresent = isCurrentLocationAlreadyPresent,
            onAddCurrentLocationClick = {
                addCurrentLocationLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            },
            onAddLocationClick = {
                viewModel.insertLocation(it)
            },
            paddingValues = paddingValues
        )
    }
}