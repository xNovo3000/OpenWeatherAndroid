package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class WindSpeedUnit(
    @StringRes val stringRes: Int,
    val queryName: String,
    val converter: (Int) -> Int
) {

    KMH(R.string.wind_speed_unit_kmh, "kmh", { it }),
    MS(R.string.wind_speed_unit_ms, "ms", { (it / 3.6).toInt() }),
    MPH(R.string.wind_speed_unit_mph, "mph", { (it / 1.609).toInt() }),
    KN(R.string.wind_speed_unit_kn, "kn", { (it / 1.852).toInt() });

    companion object {

        private fun fromInt(value: Int): WindSpeedUnit? {
            return when (value) {
                0 -> KMH
                1 -> MS
                2 -> MPH
                3 -> KN
                else -> null
            }
        }

        fun fromIntOrDefault(value: Int): WindSpeedUnit {
            return fromInt(value) ?: KMH
        }

    }

}