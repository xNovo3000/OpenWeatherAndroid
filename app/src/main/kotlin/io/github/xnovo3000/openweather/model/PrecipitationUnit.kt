package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class PrecipitationUnit(
    @StringRes val stringRes: Int,
    val queryName: String,
    val converter: (Int) -> Int
) {

    MM(R.string.precipitation_unit_mm, "mm", { it }),
    INCH(R.string.precipitation_unit_inch, "inch", { (it / 25.4).toInt() });

    companion object {

        private fun fromString(value: String): PrecipitationUnit? {
            return when (value) {
                "mm" -> MM
                "inch" -> INCH
                else -> null
            }
        }

        fun fromStringOrDefault(value: String): PrecipitationUnit {
            return fromString(value) ?: MM
        }

    }

}