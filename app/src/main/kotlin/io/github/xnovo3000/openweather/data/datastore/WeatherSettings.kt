package io.github.xnovo3000.openweather.data.datastore

import io.github.xnovo3000.openweather.data.model.QueryLanguage
import io.github.xnovo3000.openweather.data.model.TemperatureUnit
import io.github.xnovo3000.openweather.data.model.WindSpeedUnit
import kotlinx.serialization.Serializable

@Serializable
data class WeatherSettings(
    val queryLanguage: QueryLanguage,
    val temperatureUnit: TemperatureUnit,
    val windSpeedUnit: WindSpeedUnit
)