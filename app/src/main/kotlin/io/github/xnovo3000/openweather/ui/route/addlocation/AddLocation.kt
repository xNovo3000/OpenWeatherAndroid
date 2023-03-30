package io.github.xnovo3000.openweather.ui.route.addlocation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.ui.core.withNavigationBarPadding

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun AddLocationScreen(
    navController: NavController,
    viewModel: AddLocationViewModel = hiltViewModel()
) {
    // Screen state
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val focusRequester = remember { FocusRequester() }
    val textInputService = LocalSoftwareKeyboardController.current
    // Show keyboard when building for the first time
    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        textInputService?.show()
    }
    // Build screen
    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            // Query state
            val query by viewModel.query.collectAsStateWithLifecycle()
            // Build top bar
            AddLocationTopBar(
                query = query,
                onQueryChange = { viewModel.search(it) },
                onSearchClick = { textInputService?.hide() },
                focusRequester = focusRequester,
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                onChangeLanguageClick = { /*TODO*/ },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        // TODO: Listen state
        val isCurrentLocationPresent by viewModel.isCurrentLocationPresent.collectAsStateWithLifecycle()
        val geocodedLocationItems by viewModel.geocodedLocationItems.collectAsStateWithLifecycle()
        // Build content
        AddLocationContent(
            isCurrentLocationPresent = isCurrentLocationPresent,
            onAddCurrentLocationClick = {
                viewModel.addLocation(
                    id = 0,
                    name = "",
                    latitude = 0.0,
                    longitude = 0.0
                )
            },
            geocodedLocationItemList = geocodedLocationItems,
            onAddLocationClick = {
                viewModel.addLocation(
                    id = it.id,
                    name = it.name,
                    latitude = it.latitude,
                    longitude = it.longitude
                )
            },
            paddingValues = paddingValues.withNavigationBarPadding
        )
    }
    // TODO: Build dialog to change query language
}