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

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun AddLocationScreen(
    navController: NavController,
    viewModel: AddLocationViewModel = hiltViewModel()
) {
    // Dialog state
    var isChangeLanguageDialogOpen by remember { mutableStateOf(false) }
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
                focusRequester = focusRequester,
                onNavigationIconClick = {
                    navController.popBackStack()
                },
                onChangeLanguageClick = { isChangeLanguageDialogOpen = true },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        // Listen state
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
            paddingValues = paddingValues
        )
    }
    // Build dialog to change query language
    if (isChangeLanguageDialogOpen) {
        // Current selected language
        var selectedLanguage by remember { mutableStateOf(viewModel.queryLanguageFlow.value) }
        // Build language chooser
        AddLocationChangeLanguageDialog(
            selectedLanguage = selectedLanguage,
            onLanguageSelect = { selectedLanguage = it },
            onDismissRequest = { isChangeLanguageDialogOpen = false },
            onConfirm = {
                viewModel.changeQueryLanguage(selectedLanguage)
                isChangeLanguageDialogOpen = false
            }
        )
    }
}