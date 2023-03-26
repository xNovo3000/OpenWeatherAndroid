package io.github.xnovo3000.openweather.retrofit.api

import io.github.xnovo3000.openweather.retrofit.dto.ForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly", encoded = true) hourly: String = "temperature_2m,relativehumidity_2m,weathercode,surface_pressure,windspeed_10m,winddirection_10m",
        @Query("daily", encoded = true) daily: String = "weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_probability_max",
        @Query("timezone") timezone: String = "auto"
    ): ForecastDto?

}