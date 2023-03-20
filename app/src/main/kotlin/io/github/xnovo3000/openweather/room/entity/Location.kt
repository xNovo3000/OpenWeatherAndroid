package io.github.xnovo3000.openweather.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    indices = [
        Index(
            value = ["name"],
            unique = true
        )
    ]
)
data class Location(
    @PrimaryKey val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val lastUpdate: LocalDateTime,
    val sequence: Int
)