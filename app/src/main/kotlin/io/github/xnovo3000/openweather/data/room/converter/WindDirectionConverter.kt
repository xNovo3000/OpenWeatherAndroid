package io.github.xnovo3000.openweather.data.room.converter

import androidx.room.TypeConverter
import io.github.xnovo3000.openweather.data.model.WindDirection

object WindDirectionConverter {

    @TypeConverter
    fun fromWindDirectionToInt(value: WindDirection?): Int? {
        return when (value) {
            WindDirection.NORTH -> 0
            WindDirection.NORTH_EAST -> 1
            WindDirection.EAST -> 2
            WindDirection.SOUTH_EAST -> 3
            WindDirection.SOUTH -> 4
            WindDirection.SOUTH_WEST -> 5
            WindDirection.WEST -> 6
            WindDirection.NORTH_WEST -> 7
            else -> null
        }
    }

    @TypeConverter
    fun fromIntToWindDirection(value: Int?): WindDirection? {
        return when (value) {
            0 -> WindDirection.NORTH
            1 -> WindDirection.NORTH_EAST
            2 -> WindDirection.EAST
            3 -> WindDirection.SOUTH_EAST
            4 -> WindDirection.SOUTH
            5 -> WindDirection.SOUTH_WEST
            6 -> WindDirection.WEST
            7 -> WindDirection.NORTH_WEST
            else -> null
        }
    }

}