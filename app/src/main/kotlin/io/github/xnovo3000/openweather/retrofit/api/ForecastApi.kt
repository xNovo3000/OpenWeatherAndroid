package io.github.xnovo3000.openweather.retrofit.api

import io.github.xnovo3000.openweather.model.PrecipitationUnit
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.model.WindSpeedUnit
import io.github.xnovo3000.openweather.retrofit.dto.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("temperature_unit") temperatureUnit: String = TemperatureUnit.CELSIUS.queryName,
        @Query("windspeed_unit") windSpeedUnit: String = WindSpeedUnit.KMH.queryName,
        @Query("precipitation_unit") precipitationUnit: String = PrecipitationUnit.MM.queryName,
        @Query("hourly", encoded = true) hourly: String = "temperature_2m,relativehumidity_2m,weathercode,surface_pressure,windspeed_10m,winddirection_10m",
        @Query("daily", encoded = true) daily: String = "weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,windspeed_10m_max,windgusts_10m_max",
        @Query("timezone") timezone: String = "auto"
    ): ForecastDto?

}