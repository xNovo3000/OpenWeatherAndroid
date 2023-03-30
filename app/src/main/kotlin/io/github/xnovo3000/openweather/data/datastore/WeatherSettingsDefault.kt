package io.github.xnovo3000.openweather.data.datastore

import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import io.github.xnovo3000.openweather.data.model.QueryLanguage
import io.github.xnovo3000.openweather.data.model.TemperatureUnit
import io.github.xnovo3000.openweather.data.model.WindSpeedUnit

object WeatherSettingsDefault {

    fun queryLanguage(): QueryLanguage {
        var current: ULocale? = ULocale.getDefault()
        var found: QueryLanguage? = null
        while (current != null && found == null) {
            found = QueryLanguage.values().firstOrNull {
                current!!.isO3Language.contains(it.queryName)
            }
            current = current.fallback
        }
        return found ?: QueryLanguage.ENGLISH
    }

    fun temperatureUnit(): TemperatureUnit {
        return when (LocaleData.getMeasurementSystem(ULocale.getDefault())) {
            // LocaleData.MeasurementSystem.SI -> TemperatureUnit.CELSIUS
            // LocaleData.MeasurementSystem.UK -> TemperatureUnit.CELSIUS
            LocaleData.MeasurementSystem.US -> TemperatureUnit.FAHRENHEIT
            else -> TemperatureUnit.CELSIUS
        }
    }

    fun windSpeedUnit(): WindSpeedUnit {
        return when (LocaleData.getMeasurementSystem(ULocale.getDefault())) {
            LocaleData.MeasurementSystem.SI -> WindSpeedUnit.KMH
            // LocaleData.MeasurementSystem.UK -> WindSpeedUnit.MPH
            // LocaleData.MeasurementSystem.US -> WindSpeedUnit.MPH
            else -> WindSpeedUnit.MPH
        }
    }

}