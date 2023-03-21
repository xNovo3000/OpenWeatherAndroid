package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class TemperatureUnit(
    @StringRes val stringRes: Int,
    val queryName: String,
    val converter: (Int) -> Int
) {

    CELSIUS(R.string.temperature_unit_celsius, "celsius", { it }),
    FAHRENHEIT(R.string.temperature_unit_fahrenheit, "fahrenheit", { (it * 1.8 + 32).toInt() });

    companion object {

        private fun fromString(value: String): TemperatureUnit? {
            return when (value) {
                "celsius" -> CELSIUS
                "fahrenheit" -> FAHRENHEIT
                else -> null
            }
        }

        fun fromStringOrDefault(value: String): TemperatureUnit {
            return fromString(value) ?: CELSIUS
        }

    }

}