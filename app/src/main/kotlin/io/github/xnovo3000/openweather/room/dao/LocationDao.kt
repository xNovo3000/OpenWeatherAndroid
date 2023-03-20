package io.github.xnovo3000.openweather.room.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.xnovo3000.openweather.room.view.LocationWithCurrentForecast
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("select * from LocationWithCurrentForecast")
    fun listenAllWithCurrentForecast(): Flow<List<LocationWithCurrentForecast>>

}