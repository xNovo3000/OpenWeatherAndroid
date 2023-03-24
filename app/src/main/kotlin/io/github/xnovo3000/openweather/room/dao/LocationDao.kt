package io.github.xnovo3000.openweather.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import io.github.xnovo3000.openweather.room.view.LocationWithCurrentForecast
import io.github.xnovo3000.openweather.room.view.LocationWithForecasts
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from LocationWithCurrentForecast")
    fun listenAllWithCurrentForecast(): Flow<List<LocationWithCurrentForecast>>

    @Transaction
    @Query("select * from Location where id = :id")
    fun listenByLocationIdWithForecast(id: Long): Flow<LocationWithForecasts?>

}