package io.github.xnovo3000.openweather.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.xnovo3000.openweather.data.room.converter.LocalDateConverter
import io.github.xnovo3000.openweather.data.room.converter.LocalDateTimeConverter
import io.github.xnovo3000.openweather.data.room.converter.WeatherCodeConverter
import io.github.xnovo3000.openweather.data.room.converter.WindDirectionConverter
import io.github.xnovo3000.openweather.data.room.dao.DayForecastDao
import io.github.xnovo3000.openweather.data.room.dao.HourForecastDao
import io.github.xnovo3000.openweather.data.room.dao.LocationDao
import io.github.xnovo3000.openweather.data.room.entity.DayForecast
import io.github.xnovo3000.openweather.data.room.entity.HourForecast
import io.github.xnovo3000.openweather.data.room.entity.Location
import io.github.xnovo3000.openweather.data.room.view.LocationWithCurrentForecast

@Database(
    entities = [
        Location::class,
        HourForecast::class,
        DayForecast::class
    ],
    views = [
        LocationWithCurrentForecast::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        LocalDateConverter::class,
        LocalDateTimeConverter::class,
        WeatherCodeConverter::class,
        WindDirectionConverter::class
    ]
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
    abstract fun getHourForecastDao(): HourForecastDao
    abstract fun getDayForecastDao(): DayForecastDao
}