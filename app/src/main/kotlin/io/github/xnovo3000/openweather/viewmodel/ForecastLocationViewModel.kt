package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.Settings
import io.github.xnovo3000.openweather.room.WeatherDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ForecastLocationViewModel @Inject constructor(
    weatherDatabase: WeatherDatabase,
    savedStateHandle: SavedStateHandle,
    settings: DataStore<Settings>
) : ViewModel() {

    private val locationId: Long = savedStateHandle["id"]!!
    private val locationWithForecastFlow = weatherDatabase.getLocationDao().listenByLocationIdWithForecast(locationId)

    val locationName = locationWithForecastFlow.map { it?.location?.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

}