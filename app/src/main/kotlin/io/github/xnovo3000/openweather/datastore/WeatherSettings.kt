package io.github.xnovo3000.openweather.datastore

import io.github.xnovo3000.openweather.model.QueryLanguage
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.model.WindSpeedUnit
import kotlinx.serialization.Serializable

@Serializable
data class WeatherSettings(
    val queryLanguage: QueryLanguage,
    val temperatureUnit: TemperatureUnit,
    val windSpeedUnit: WindSpeedUnit
)