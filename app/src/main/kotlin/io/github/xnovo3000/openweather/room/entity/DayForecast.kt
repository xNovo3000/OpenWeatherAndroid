package io.github.xnovo3000.openweather.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.xnovo3000.openweather.model.WeatherCode
import io.github.xnovo3000.openweather.model.WindDirection
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    indices = [
        Index(
            value = ["locationId"]
        ),
        Index(
            value = ["date", "locationId"],
            unique = true
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["locationId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DayForecast(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long,
    val date: LocalDate,
    val weatherCode: WeatherCode,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val windSpeed: Int,
    val windDirection: WindDirection
)
