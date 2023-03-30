package io.github.xnovo3000.openweather.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.xnovo3000.openweather.data.datastore.WeatherSettings
import io.github.xnovo3000.openweather.data.datastore.WeatherSettingsSerializer
import io.github.xnovo3000.openweather.api.retrofit.api.ForecastApi
import io.github.xnovo3000.openweather.data.room.WeatherDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, JsonDeserializer { json, _, _ ->
                LocalDate.parse(json.asJsonPrimitive.asString)
            })
            .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
                LocalDateTime.parse(json.asJsonPrimitive.asString)
            })
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun providesForecastApi(gsonConverterFactory: GsonConverterFactory): ForecastApi {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return Room
            .databaseBuilder(context, WeatherDatabase::class.java, "weather-database")
            .build()
    }

    @Provides
    @Singleton
    fun providesWeatherSettings(@ApplicationContext context: Context): DataStore<WeatherSettings> {
        return DataStoreFactory.create(
            serializer = WeatherSettingsSerializer,
            produceFile = { context.dataStoreFile("weather_settings.json") }
        )
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

}