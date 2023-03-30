package io.github.xnovo3000.openweather.data.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.xnovo3000.openweather.data.model.WeatherCode
import io.github.xnovo3000.openweather.data.model.WindDirection
import java.time.LocalDateTime

@Entity(
    indices = [
        Index(
            value = ["locationId"]
        ),
        Index(
            value = ["dateTime", "locationId"],
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
data class HourForecast (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val locationId: Long,
    val dateTime: LocalDateTime,
    val temperature: Int,
    val humidity: Int,
    val weatherCode: WeatherCode,
    val pressure: Int,
    val windSpeed: Int,
    val windDirection: WindDirection
)