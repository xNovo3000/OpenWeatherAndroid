package io.github.xnovo3000.openweather.room.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object LocalDateTimeConverter {

    @TypeConverter
    fun fromLocalDateTimeToLong(value: LocalDateTime?): Long? {
        return value?.atZone(ZoneId.systemDefault())?.toEpochSecond()
    }

    @TypeConverter
    fun fromLongToLocalDateTime(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
        }
    }

}