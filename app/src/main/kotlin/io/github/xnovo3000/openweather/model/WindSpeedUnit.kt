package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class WindSpeedUnit(@StringRes val stringRes: Int, val converter: (Int) -> Int) {
    KMH(R.string.wind_speed_unit_kmh, { it }),
    MS(R.string.wind_speed_unit_ms, { (it / 3.6).toInt() }),
    MPH(R.string.wind_speed_unit_mph, { (it / 1.609).toInt() }),
    KN(R.string.wind_speed_unit_kn, { (it / 1.852).toInt() });
}