package io.github.xnovo3000.openweather.api.retrofit.dto

data class ForecastDto(
    val hourly: HourlyForecastDto?,
    val daily: DailyForecastDto?
)