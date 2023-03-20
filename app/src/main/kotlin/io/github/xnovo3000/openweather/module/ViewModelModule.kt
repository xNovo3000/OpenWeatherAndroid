package io.github.xnovo3000.openweather.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.xnovo3000.openweather.retrofit.api.GeocodingApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providesGeocodingApi(gsonConverterFactory: GsonConverterFactory): GeocodingApi {
        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/v1/")
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create()
    }

}