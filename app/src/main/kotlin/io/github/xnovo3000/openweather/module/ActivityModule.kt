package io.github.xnovo3000.openweather.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.xnovo3000.openweather.datastore.WeatherSettings
import io.github.xnovo3000.openweather.datastore.WeatherSettingsSerializer

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
    fun providesWeatherSettings(@ApplicationContext context: Context): DataStore<WeatherSettings> {
        return DataStoreFactory.create(
            serializer = WeatherSettingsSerializer,
            produceFile = { context.dataStoreFile("settings.pb") }
        )
    }

}