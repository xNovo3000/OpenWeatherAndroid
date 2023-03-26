package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
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

        fun default(): WindSpeedUnit {
            return when (LocaleData.getMeasurementSystem(ULocale.getDefault())) {
                // LocaleData.MeasurementSystem.SI -> KMH
                // LocaleData.MeasurementSystem.UK -> KMH
                LocaleData.MeasurementSystem.US -> MPH
                else -> KMH
            }
        }

    }

}