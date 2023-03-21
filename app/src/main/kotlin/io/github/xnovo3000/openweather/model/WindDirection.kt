package io.github.xnovo3000.openweather.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.github.xnovo3000.openweather.R

enum class WindDirection(
    @DrawableRes val icon: Int,
    @StringRes val displayName: Int
) {

    NORTH(R.drawable.wd_north, R.string.wind_direction_north),
    NORTH_EAST(R.drawable.wd_north_east, R.string.wind_direction_north_east),
    EAST(R.drawable.wd_east, R.string.wind_direction_east),
    SOUTH_EAST(R.drawable.wd_south_east, R.string.wind_direction_south_east),
    SOUTH(R.drawable.wd_south, R.string.wind_direction_south),
    SOUTH_WEST(R.drawable.wd_south_west, R.string.wind_direction_south_west),
    WEST(R.drawable.wd_west, R.string.wind_direction_west),
    NORTH_WEST(R.drawable.wd_north_west, R.string.wind_direction_north_west);

    companion object {

        private fun fromInt(value: Int): WindDirection? {
            return when (value) {
                0 -> NORTH
                1 -> NORTH_EAST
                2 -> EAST
                3 -> SOUTH_EAST
                4 -> SOUTH
                5 -> SOUTH_WEST
                6 -> WEST
                7 -> NORTH_WEST
                else -> null
            }
        }

        fun fromIntOrDefault(value: Int): WindDirection {
            return fromInt(value) ?: NORTH
        }

    }

}