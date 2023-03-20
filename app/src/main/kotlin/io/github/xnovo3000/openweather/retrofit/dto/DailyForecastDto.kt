package io.github.xnovo3000.openweather.retrofit.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class DailyForecastDto(
    val time: List<LocalDate>,
    val weathercode: List<Int>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val sunrise: List<LocalDateTime>,
    val sunset: List<LocalDateTime>,
    val windspeed_10m_max: List<Double>,
    val winddirection_10m_dominant: List<Int>
)