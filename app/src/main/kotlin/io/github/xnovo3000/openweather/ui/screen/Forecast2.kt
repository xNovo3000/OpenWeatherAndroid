package io.github.xnovo3000.openweather.ui.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import io.github.xnovo3000.openweather.viewmodel.Forecast2ViewModel

@ExperimentalMaterial3Api
@Composable
fun Forecast2Screen(
    navController: NavController,
    viewModel: Forecast2ViewModel = hiltViewModel()
) {
    Scaffold { paddingValues ->
        paddingValues
    }
}