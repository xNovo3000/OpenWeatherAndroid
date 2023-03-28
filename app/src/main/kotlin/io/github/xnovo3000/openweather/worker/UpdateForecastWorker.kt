package io.github.xnovo3000.openweather.worker

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.hasKeyWithValueOfType
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Tasks
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.github.xnovo3000.openweather.model.WeatherCode
import io.github.xnovo3000.openweather.model.WindDirection
import io.github.xnovo3000.openweather.retrofit.api.ForecastApi
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.room.entity.DayForecast
import io.github.xnovo3000.openweather.room.entity.HourForecast
import java.io.IOException

@HiltWorker
class UpdateForecastWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherDatabase: WeatherDatabase,
    private val forecastApi: ForecastApi,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TAG_ONE_TIME = "UpdateForecastWorker_ONE"
        const val TAG_PERIODIC = "UpdateForecastWorker_PERIODIC"
    }

    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        // Get ID of the location to update
        val locationId = if (inputData.hasKeyWithValueOfType<Long>("id")) {
            // Get from input
            inputData.getLong("id", -1L)
        } else {
            // Get favorite location
            weatherDatabase.getLocationDao().getIdGroupByMinSequence() ?: return Result.failure()
        }
        // Get Location object
        var location = weatherDatabase.getLocationDao().getById(locationId) ?: return Result.failure()
        // If the location is the current one, update the location first
        if (location.id == 0L) {
            // Check for location permission
            if (!hasAccessCoarseLocationPermission) {
                return Result.failure()
            }
            // Get current location
            Tasks.await(fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, null))
                ?.let { currentLocation ->
                    // Update location entity if there is a location
                    location = location.copy(
                        latitude = currentLocation.latitude,
                        longitude = currentLocation.longitude
                    )
                    // Save to database
                    weatherDatabase.getLocationDao().update(location)
                }
        }
        // Get forecast from the location
        val forecast = try {
            forecastApi.getForecast(
                latitude = location.latitude,
                longitude = location.longitude
            ) ?: return Result.failure()
        } catch (e: IOException) {
            e.printStackTrace()
            return Result.failure()
        }
        // Create all the new data structures for hourly and daily
        val hourlyForecast = forecast.hourly?.let { hourlyForecastDto ->
            hourlyForecastDto.time.mapIndexed { index, localDateTime ->
                HourForecast(
                    id = 0,
                    locationId = location.id,
                    dateTime = localDateTime,
                    temperature = hourlyForecastDto.temperature_2m[index].toInt(),
                    humidity = hourlyForecastDto.relativehumidity_2m[index].toInt(),
                    weatherCode = getWeatherCode(hourlyForecastDto.weathercode[index]),
                    pressure = hourlyForecastDto.surface_pressure[index].toInt(),
                    windSpeed = hourlyForecastDto.windspeed_10m[index].toInt(),
                    windDirection = getWindDirection(hourlyForecastDto.winddirection_10m[index]),
                )
            }
        } ?: return Result.failure()
        val dailyForecast = forecast.daily?.let { dailyForecastDto ->
            dailyForecastDto.time.mapIndexed { index, localDate ->
                DayForecast(
                    id = 0,
                    locationId = location.id,
                    date = localDate,
                    weatherCode = getWeatherCode(dailyForecastDto.weathercode[index]),
                    temperatureMin = dailyForecastDto.temperature_2m_min[index].toInt(),
                    temperatureMax = dailyForecastDto.temperature_2m_max[index].toInt(),
                    sunrise = dailyForecastDto.sunrise[index],
                    sunset = dailyForecastDto.sunset[index],
                    precipitationProbability = dailyForecastDto.precipitation_probability_max[index]
                )
            }
        } ?: return Result.failure()
        // Update everything in a unique transaction
        weatherDatabase.withTransaction {
            weatherDatabase.getDayForecastDao().deleteByLocationId(location.id)
            weatherDatabase.getHourForecastDao().deleteByLocationId(location.id)
            weatherDatabase.getDayForecastDao().insertAll(dailyForecast)
            weatherDatabase.getHourForecastDao().insertAll(hourlyForecast)
        }
        // Return success
        return Result.success()
    }

    private fun getWeatherCode(code: Int): WeatherCode {
        return WeatherCode.values().firstOrNull {
            it.wmoAcceptedCodes.contains(code)
        } ?: WeatherCode.CLEAR
    }

    private fun getWindDirection(direction: Int): WindDirection {
        return when (direction) {
            in 23..67 -> WindDirection.NORTH_EAST
            in 68..112 -> WindDirection.EAST
            in 113..157 -> WindDirection.SOUTH_EAST
            in 158..202 -> WindDirection.SOUTH
            in 203..247 -> WindDirection.SOUTH_WEST
            in 248..292 -> WindDirection.WEST
            in 293..337 -> WindDirection.NORTH_WEST
            else -> WindDirection.NORTH
        }
    }

    private val hasAccessCoarseLocationPermission: Boolean
        get() = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

}