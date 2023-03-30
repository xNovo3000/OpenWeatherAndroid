package io.github.xnovo3000.openweather.ui.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun WeatherSurface(content: @Composable () -> Unit) {
    // Get current state
    val useDarkIcons = !isSystemInDarkTheme()
    // Set system bars color
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }
    // Draw the main surface
    Surface(
        modifier = Modifier.fillMaxSize(),
        content = content
    )
}