package io.github.xnovo3000.openweather.core.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import dagger.hilt.android.AndroidEntryPoint
import io.github.xnovo3000.openweather.data.datastore.WeatherSettings
import io.github.xnovo3000.openweather.data.datastore.WeatherSettingsSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocaleChangedBroadcastReceiver : BroadcastReceiver() {

    @Inject lateinit var data: DataStore<WeatherSettings>

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_LOCALE_CHANGED) {
            CoroutineScope(Dispatchers.Default).launch {
                // Reset settings values when the locale changes
                data.updateData { WeatherSettingsSerializer.defaultValue }
            }
        }
    }

}