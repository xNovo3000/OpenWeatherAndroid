package io.github.xnovo3000.openweather.api.retrofit.dto

import java.time.LocalDateTime

data class HourlyForecastDto(
    val time: List<LocalDateTime>,
    val temperature_2m: List<Double>,
    val relativehumidity_2m: List<Double>,
    val weathercode: List<Int>,
    val surface_pressure: List<Double>,
    val windspeed_10m: List<Double>,
    val winddirection_10m: List<Int>
)