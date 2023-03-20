package io.github.xnovo3000.openweather.room.view

import androidx.room.DatabaseView
import io.github.xnovo3000.openweather.model.WeatherCode
import java.time.LocalDateTime

@DatabaseView(
    value = """
        select l.id, l.name, l.lastUpdate, l.sequence, h.weatherCode, h.temperature,
            d.sunrise, d.sunset, min(h.dateTime), min(d.date)
        from Location l
        left join HourForecast h on l.id = h.locationId
        left join DayForecast d on l.id = d.locationId
        group by l.id
    """
)
data class LocationWithCurrentForecast(
    val id: Long,
    val name: String,
    val lastUpdate: LocalDateTime,
    val sequence: Int,
    val temperature: Int?,
    val weatherCode: WeatherCode?,
    val sunrise: LocalDateTime?,
    val sunset: LocalDateTime?
)