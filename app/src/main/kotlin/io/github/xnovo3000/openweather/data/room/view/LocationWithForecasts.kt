package io.github.xnovo3000.openweather.data.room.view

import androidx.room.Embedded
import androidx.room.Relation
import io.github.xnovo3000.openweather.data.room.entity.DayForecast
import io.github.xnovo3000.openweather.data.room.entity.HourForecast
import io.github.xnovo3000.openweather.data.room.entity.Location

data class LocationWithForecasts(
    @Embedded val location: Location,
    @Relation(parentColumn = "id", entityColumn = "locationId") val hourlyForecast: List<HourForecast>,
    @Relation(parentColumn = "id", entityColumn = "locationId") val dailyForecast: List<DayForecast>
)