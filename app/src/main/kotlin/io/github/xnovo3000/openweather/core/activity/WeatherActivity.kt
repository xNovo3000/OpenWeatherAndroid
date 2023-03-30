package io.github.xnovo3000.openweather.core.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.xnovo3000.openweather.ui.WeatherUI

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {

    @OptIn(
        ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
        ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
        ExperimentalMaterial3Api::class,
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent { WeatherUI() }
    }

}