package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.data.datastore.WeatherSettings
import io.github.xnovo3000.openweather.data.room.WeatherDatabase
import javax.inject.Inject

@HiltViewModel
class Forecast2ViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    settings: DataStore<WeatherSettings>
) : ViewModel() {

    private val locationsFlow = weatherDatabase.getLocationDao().listenAllWithForecastsOrderBySequenceAsc()

}