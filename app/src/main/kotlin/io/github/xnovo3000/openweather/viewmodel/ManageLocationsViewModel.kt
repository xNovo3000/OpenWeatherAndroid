package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.Settings
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.ui.item.ManageLocationsLocationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManageLocationsViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    settings: DataStore<Settings>
) : ViewModel() {

    private val locationsFlow = weatherDatabase.getLocationDao().listenAllWithCurrentForecast()
    private val temperatureUnitFlow = settings.data.map {
        withContext(Dispatchers.Default) {
            TemperatureUnit.fromStringOrDefault(it.temperatureUnit)
        }
    }

    val locations = combine(locationsFlow, temperatureUnitFlow) { locations, temperatureUnit ->
        withContext(Dispatchers.Default) {
            locations.map {
                ManageLocationsLocationItem(
                    id = it.id,
                    name = it.name,
                    lastUpdate = it.lastUpdate,
                    temperature = it.temperature,
                    weatherCode = it.weatherCode,
                    isNight = it.lastUpdate < it.sunrise || it.lastUpdate > it.sunset,
                    temperatureUnit = temperatureUnit
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}