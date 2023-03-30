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
    fun listenAllWithCurrentForecastOrderBySequenceAsc(): Flow<List<LocationWithCurrentForecast>>

    @Transaction
    @Query("select * from Location where id = :id")
    fun listenByLocationIdWithForecast(id: Long): Flow<LocationWithForecasts?>

    @Query("select * from Location order by sequence asc")
    fun listenAll(): Flow<List<Location>>

    @Transaction
    @Query("select * from Location order by sequence asc")
    fun listenAllWithForecastsOrderBySequenceAsc(): Flow<List<LocationWithForecasts>>

    @Query("select max(sequence) from Location")
    suspend fun getSequenceGroupByMaxSequence(): Int?

    @RewriteQueriesToDropUnusedColumns
    @Query("select id as value, min(sequence) from Location")
    suspend fun getIdGroupByMinSequence(): Long?

    @Query("select * from Location where id = :id")
    suspend fun getById(id: Long): Location?

    @Insert
    suspend fun insert(location: Location)

    @Update
    suspend fun update(location: Location)

}