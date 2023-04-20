package io.github.xnovo3000.openweather.data.room.dao

import androidx.room.*
import io.github.xnovo3000.openweather.data.room.entity.Location
import io.github.xnovo3000.openweather.data.room.view.LocationWithCurrentForecast
import io.github.xnovo3000.openweather.data.room.view.LocationWithForecasts
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @RewriteQueriesToDropUnusedColumns
    @Query("select * from LocationWithCurrentForecast order by sequence asc")
    fun listenAllWithCurrentForecast(): Flow<List<LocationWithCurrentForecast>>

    @Query("select * from Location order by sequence asc")
    fun listenAll(): Flow<List<Location>>

    @Transaction
    @Query("select * from Location order by sequence asc")
    fun listenAllWithForecasts(): Flow<List<LocationWithForecasts>>

    @Query("select max(sequence) from Location")
    suspend fun getLeastFavoriteLocation(): Int?

    @RewriteQueriesToDropUnusedColumns
    @Query("select id as value, min(sequence) from Location")
    suspend fun getFavoriteLocation(): Long?

    @Query("select * from Location where id = :id")
    suspend fun getById(id: Long): Location?

    @Insert
    suspend fun insert(location: Location)

    @Update
    suspend fun update(location: Location)

}