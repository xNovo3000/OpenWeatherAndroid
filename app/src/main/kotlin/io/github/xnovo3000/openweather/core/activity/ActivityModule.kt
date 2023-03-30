package io.github.xnovo3000.openweather.core.activity

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.xnovo3000.openweather.api.retrofit.api.GeocodingApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {

    @Provides
    @ActivityRetainedScoped
    fun providesWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @ActivityRetainedScoped
    fun providesGeocodingApi(gsonConverterFactory: GsonConverterFactory): GeocodingApi {
        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/v1/")
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create()
    }

}