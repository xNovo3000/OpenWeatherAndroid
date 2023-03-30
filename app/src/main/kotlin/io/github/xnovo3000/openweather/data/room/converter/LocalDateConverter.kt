package io.github.xnovo3000.openweather.data.room.converter

import androidx.room.TypeConverter
import java.time.LocalDate

object LocalDateConverter {

    @TypeConverter
    fun fromLocalDateToLong(value: LocalDate?): Long? {
        return value?.toEpochDay()
    }

    @TypeConverter
    fun fromLongToLocalDate(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(it) }
    }

}