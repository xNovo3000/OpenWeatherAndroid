package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.datastore.WeatherSettings
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.ui.item.ManagedLocationItem
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
    settings: DataStore<WeatherSettings>
) : ViewModel() {

    private val locationsFlow = weatherDatabase.getLocationDao().listenAllWithCurrentForecast()
    private val temperatureUnitFlow = settings.data.map { it.temperatureUnit }

    val locations = combine(locationsFlow, temperatureUnitFlow) { locations, temperatureUnit ->
        withContext(Dispatchers.Default) {
            locations.map {
                ManagedLocationItem(
                    id = it.id,
                    name = it.name,
                    lastUpdate = it.lastUpdate,
                    temperature = it.temperature,
                    weatherCode = it.weatherCode,
                    isNight = if (it.sunrise != null && it.sunset != null) {
                        it.lastUpdate < it.sunrise || it.lastUpdate > it.sunset
                    } else false,
                    temperatureUnit = temperatureUnit
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}