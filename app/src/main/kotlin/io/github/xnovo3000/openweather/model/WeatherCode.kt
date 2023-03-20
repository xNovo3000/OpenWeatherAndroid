package io.github.xnovo3000.openweather.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R
import java.time.LocalDateTime

enum class WeatherCode(
    @DrawableRes val day: Int,
    @DrawableRes val night: Int = day,
    @StringRes val displayName: Int,
    val wmoAcceptedCodes: Set<Int>
) {

    CLEAR(
        day = R.drawable.wc_clear_day,
        night = R.drawable.wc_clear_night,
        displayName = R.string.weather_code_clear,
        wmoAcceptedCodes = hashSetOf(0)
    ),
    PARTLY_CLOUDY(
        day = R.drawable.wc_partly_cloudy_day,
        night = R.drawable.wc_partly_cloudy_night,
        displayName = R.string.weather_code_partly_cloudy,
        wmoAcceptedCodes = hashSetOf(1, 2)
    ),
    CLOUDY(
        day = R.drawable.wc_cloudy,
        displayName = R.string.weather_code_cloudy,
        wmoAcceptedCodes = hashSetOf(3)
    ),
    FOGGY(
        day = R.drawable.wc_foggy,
        displayName = R.string.weather_code_foggy,
        wmoAcceptedCodes = hashSetOf(45, 48)
    ),
    RAINY(
        day = R.drawable.wc_rainy,
        displayName = R.string.weather_code_rainy,
        wmoAcceptedCodes = hashSetOf(51, 53, 55, 56, 57, 61, 63, 65, 66, 67, 80, 81, 82)
    ),
    SNOWY(
        day = R.drawable.wc_snowy,
        displayName = R.string.weather_code_snowy,
        wmoAcceptedCodes = hashSetOf(71, 73, 75, 77, 85, 86)
    ),
    THUNDERSTORM(
        day = R.drawable.wc_thunderstorm,
        displayName = R.string.weather_code_thunderstorm,
        wmoAcceptedCodes = hashSetOf(95, 96, 99)
    );

    @DrawableRes
    fun getIcon(sunrise: LocalDateTime, sunset: LocalDateTime, now: LocalDateTime): Int {
        return if (now > sunrise && now < sunset) {
            day
        } else {
            night
        }
    }

}