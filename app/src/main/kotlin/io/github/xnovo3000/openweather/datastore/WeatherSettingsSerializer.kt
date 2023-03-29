package io.github.xnovo3000.openweather.datastore

import androidx.datastore.core.Serializer
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
            queryLanguage = WeatherSettingsDefault.queryLanguage(),
            temperatureUnit = WeatherSettingsDefault.temperatureUnit(),
            windSpeedUnit = WeatherSettingsDefault.windSpeedUnit()
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