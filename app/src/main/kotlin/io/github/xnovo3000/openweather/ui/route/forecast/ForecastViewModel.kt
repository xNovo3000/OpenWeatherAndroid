package io.github.xnovo3000.openweather.ui.route.forecast

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.data.datastore.WeatherSettings
import io.github.xnovo3000.openweather.data.room.WeatherDatabase
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    weatherSettings: DataStore<WeatherSettings>
) : ViewModel() {

    private val locationsFlow = weatherDatabase.getLocationDao().listenAllWithForecasts()

}