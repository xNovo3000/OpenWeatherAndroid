package io.github.xnovo3000.openweather.model

import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class PrecipitationUnit(
    @StringRes val stringRes: Int,
    val queryName: String,
    val converter: (Double) -> Double
) {
    MM(R.string.precipitation_unit_mm, "mm", { it }),
    INCH(R.string.precipitation_unit_inch, "inch", { it / 25.4 })
}