package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.Settings
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.ui.component.DrawerLocationItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    settings: DataStore<Settings>
) : ViewModel() {

    private val mutableLocations = weatherDatabase.getLocationDao().listenAllWithCurrentForecast()
    private val temperatureUnit = settings.data.map { TemperatureUnit.valueOf(it.temperatureUnit) }

    val locations = combine(mutableLocations, temperatureUnit) { locationList, unit ->
        locationList.map { location ->
            DrawerLocationItem(
                id = location.id,
                name = location.name,
                lastUpdate = location.lastUpdate,
                temperature = location.temperature?.let { unit.converter(it.toDouble()) }?.toInt(),
                weatherCode = location.weatherCode,
                sunrise = location.sunrise,
                sunset = location.sunset
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}