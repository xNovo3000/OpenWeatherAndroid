package io.github.xnovo3000.openweather.ui.route.managelocations

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.data.datastore.WeatherSettings
import io.github.xnovo3000.openweather.data.room.WeatherDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManageLocationsViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    weatherSettings: DataStore<WeatherSettings>
) : ViewModel() {

    private val locationsFlow = weatherDatabase.getLocationDao().listenAllWithCurrentForecast()

    val locations = combine(locationsFlow, weatherSettings.data) { locations, settings ->
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
                    temperatureUnit = settings.temperatureUnit
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

}