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
    NORTH_WEST(R.drawable.wd_north_west, R.string.wind_direction_north_west)
}