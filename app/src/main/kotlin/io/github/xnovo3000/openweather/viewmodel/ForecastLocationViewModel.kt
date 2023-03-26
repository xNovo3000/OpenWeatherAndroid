package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.datastore.WeatherSettings
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.ui.component.ForecastLocationCurrentItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForecastLocationViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    savedStateHandle: SavedStateHandle,
    settings: DataStore<WeatherSettings>
) : ViewModel() {

    private val locationId: Long = savedStateHandle["id"]!!
    private val locationWithForecastFlow = weatherDatabase.getLocationDao().listenByLocationIdWithForecast(locationId)

    val locationName = locationWithForecastFlow.map { it?.location?.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    val currentForecast = combine(locationWithForecastFlow, settings.data) { location, settings ->
        withContext(Dispatchers.Default) {
            if (location != null && location.hourlyForecast.isNotEmpty() && location.dailyForecast.isNotEmpty()) {
                // Get current forecast
                val now = location.hourlyForecast.first()
                val today = location.dailyForecast.first()
                // Create the object
                ForecastLocationCurrentItem(
                    temperature = now.temperature,
                    temperatureUnit = settings.temperatureUnit,
                    weatherCode = now.weatherCode,
                    isNightTime = location.location.lastUpdate < today.sunrise || location.location.lastUpdate > today.sunset
                )
            } else null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

}