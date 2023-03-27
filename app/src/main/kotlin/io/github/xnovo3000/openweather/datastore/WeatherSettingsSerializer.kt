package io.github.xnovo3000.openweather.datastore

import androidx.datastore.core.Serializer
import com.ibm.icu.util.LocaleData
import com.ibm.icu.util.ULocale
import io.github.xnovo3000.openweather.model.QueryLanguage
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.model.WindSpeedUnit
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object WeatherSettingsSerializer : Serializer<WeatherSettings> {

    override val defaultValue: WeatherSettings
        get() = WeatherSettings(
            queryLanguage = QueryLanguage.values().let { languages ->
                var current: ULocale? = ULocale.getDefault()
                var found: QueryLanguage? = null
                while (current != null && found == null) {
                    found = languages.firstOrNull {
                        current!!.isO3Language.contains(it.queryName)
                    }
                    current = current.fallback
                }
                found ?: QueryLanguage.ENGLISH
            },
            temperatureUnit = when (LocaleData.getMeasurementSystem(ULocale.getDefault())) {
                // LocaleData.MeasurementSystem.SI -> CELSIUS
                // LocaleData.MeasurementSystem.UK -> CELSIUS
                LocaleData.MeasurementSystem.US -> TemperatureUnit.FAHRENHEIT
                else -> TemperatureUnit.CELSIUS
            },
            windSpeedUnit = when (LocaleData.getMeasurementSystem(ULocale.getDefault())) {
                // LocaleData.MeasurementSystem.SI -> KMH
                // LocaleData.MeasurementSystem.UK -> KMH
                LocaleData.MeasurementSystem.US -> WindSpeedUnit.MPH
                else -> WindSpeedUnit.KMH
            }
        )

    @ExperimentalSerializationApi
    override suspend fun readFrom(input: InputStream): WeatherSettings {
        return try {
            Json.decodeFromStream(
                deserializer = WeatherSettings.serializer(),
                stream = input
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        } catch (e: IOException) {
            e.printStackTrace()
            defaultValue
        }
    }

    @ExperimentalSerializationApi
    override suspend fun writeTo(t: WeatherSettings, output: OutputStream) {
        try {
            Json.encodeToStream(
                serializer = WeatherSettings.serializer(),
                value = t,
                stream = output
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}