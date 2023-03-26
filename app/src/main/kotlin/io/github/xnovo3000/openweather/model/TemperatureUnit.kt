package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import io.github.xnovo3000.openweather.R

enum class TemperatureUnit(
    @StringRes val stringRes: Int,
    val queryName: String,
    val converter: (Int) -> Int
) {

    CELSIUS(R.string.temperature_unit_celsius, "celsius", { it }),
    FAHRENHEIT(R.string.temperature_unit_fahrenheit, "fahrenheit", { (it * 1.8 + 32).toInt() });

    companion object {

        fun default(): TemperatureUnit {
            return when (LocaleData.getMeasurementSystem(ULocale.getDefault())) {
                // LocaleData.MeasurementSystem.SI -> CELSIUS
                // LocaleData.MeasurementSystem.UK -> CELSIUS
                LocaleData.MeasurementSystem.US -> FAHRENHEIT
                else -> CELSIUS
            }
        }

    }

}