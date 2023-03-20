package io.github.xnovo3000.openweather.room.converter

import androidx.room.TypeConverter
import io.github.xnovo3000.openweather.model.WeatherCode

object WeatherCodeConverter {

    @TypeConverter
    fun fromWeatherCodeToInt(value: WeatherCode?): Int? {
        return when (value) {
            WeatherCode.CLEAR -> 0
            WeatherCode.PARTLY_CLOUDY -> 1
            WeatherCode.CLOUDY -> 2
            WeatherCode.FOGGY -> 3
            WeatherCode.RAINY -> 4
            WeatherCode.SNOWY -> 5
            WeatherCode.THUNDERSTORM -> 6
            else -> null
        }
    }

    @TypeConverter
    fun fromIntToWeatherCode(value: Int?): WeatherCode? {
        return when (value) {
            0 -> WeatherCode.CLEAR
            1 -> WeatherCode.PARTLY_CLOUDY
            2 -> WeatherCode.CLOUDY
            3 -> WeatherCode.FOGGY
            4 -> WeatherCode.RAINY
            5 -> WeatherCode.SNOWY
            6 -> WeatherCode.THUNDERSTORM
            else -> null
        }
    }

}