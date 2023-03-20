package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class WindSpeedUnit(
    @StringRes val stringRes: Int,
    val queryName: String,
    val converter: (Double) -> Double
) {
    KMH(R.string.wind_speed_unit_kmh, "kmh", { it }),
    MS(R.string.wind_speed_unit_ms, "ms", { it / 3.6 }),
    MPH(R.string.wind_speed_unit_mph, "mph", { it / 1.609 }),
    KN(R.string.wind_speed_unit_kn, "kn", { it / 1.852 }),
}