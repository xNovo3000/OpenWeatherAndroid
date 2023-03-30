package io.github.xnovo3000.openweather.data.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class TemperatureUnit(@StringRes val stringRes: Int, val converter: (Int) -> Int) {
    CELSIUS(R.string.temperature_unit_celsius, { it }),
    FAHRENHEIT(R.string.temperature_unit_fahrenheit, { (it * 1.8 + 32).toInt() });
}