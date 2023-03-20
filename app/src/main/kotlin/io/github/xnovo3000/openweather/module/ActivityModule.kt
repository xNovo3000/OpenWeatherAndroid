package io.github.xnovo3000.openweather.module

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import androidx.work.WorkManager
import com.google.protobuf.InvalidProtocolBufferException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.github.xnovo3000.openweather.Settings
import java.io.InputStream
import java.io.OutputStream

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
    fun providesWeatherSettings(@ApplicationContext context: Context): DataStore<Settings> {
        return DataStoreFactory.create(
            serializer = object : Serializer<Settings> {
                override val defaultValue: Settings
                    get() = Settings.getDefaultInstance()
                override suspend fun readFrom(input: InputStream): Settings {
                    try {
                        return Settings.parseFrom(input)
                    } catch (exception: InvalidProtocolBufferException) {
                        throw CorruptionException("Cannot read proto.", exception)
                    }
                }
                override suspend fun writeTo(t: Settings, output: OutputStream) {
                    return t.writeTo(output)
                }
            },
            produceFile = { context.dataStoreFile("settings.pb") }
        )
    }

}