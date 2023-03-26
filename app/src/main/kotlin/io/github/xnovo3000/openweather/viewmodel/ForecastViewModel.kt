package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.datastore.WeatherSettings
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.ui.component.DrawerLocationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    settings: DataStore<WeatherSettings>
) : ViewModel() {

    private val locationsFlow = weatherDatabase.getLocationDao().listenAllWithCurrentForecast()
    private val temperatureUnitFlow = settings.data.map { it.temperatureUnit }

    val locations = combine(locationsFlow, temperatureUnitFlow) { locationList, temperatureUnit ->
        withContext(Dispatchers.Default) {
            locationList.map { location ->
                DrawerLocationItem(
                    id = location.id,
                    name = location.name,
                    temperature = location.temperature?.let { temperatureUnit.converter(it) },
                    weatherCode = location.weatherCode,
                    isNightTime = location.lastUpdate < location.sunrise || location.lastUpdate > location.sunset
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}