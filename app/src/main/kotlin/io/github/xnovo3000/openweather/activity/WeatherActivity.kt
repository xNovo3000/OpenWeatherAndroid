package io.github.xnovo3000.openweather.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.xnovo3000.openweather.ui.WeatherUI

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent { WeatherUI() }
    }

}