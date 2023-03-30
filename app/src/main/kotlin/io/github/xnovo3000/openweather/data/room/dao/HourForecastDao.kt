package io.github.xnovo3000.openweather.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.xnovo3000.openweather.data.room.entity.HourForecast

@Dao
interface HourForecastDao {

    @Query("delete from HourForecast where locationId = :locationId")
    suspend fun deleteByLocationId(locationId: Long)

    @Insert
    suspend fun insertAll(values: List<HourForecast>)

}