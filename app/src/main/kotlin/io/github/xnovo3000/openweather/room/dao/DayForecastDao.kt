package io.github.xnovo3000.openweather.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.xnovo3000.openweather.room.entity.DayForecast

@Dao
interface DayForecastDao {

    @Query("delete from DayForecast where locationId = :locationId")
    suspend fun deleteByLocationId(locationId: Long)

    @Insert
    suspend fun insertAll(values: List<DayForecast>)

}