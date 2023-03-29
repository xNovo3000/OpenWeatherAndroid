package io.github.xnovo3000.openweather.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.datastore.WeatherSettings
import io.github.xnovo3000.openweather.retrofit.api.GeocodingApi
import io.github.xnovo3000.openweather.retrofit.dto.GeocodedLocationDto
import io.github.xnovo3000.openweather.room.WeatherDatabase
import io.github.xnovo3000.openweather.room.entity.Location
import io.github.xnovo3000.openweather.ui.item.GeocodedLocationItem
import io.github.xnovo3000.openweather.worker.UpdateForecastWorker
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class FindLocationViewModel @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val settings: DataStore<WeatherSettings>,
    private val weatherDatabase: WeatherDatabase,
    private val workManager: WorkManager
) : ViewModel() {

    private val locationIdsFlow = weatherDatabase.getLocationDao().listenAllWithCurrentForecast()
        .map { locations -> locations.map { it.id } }
    private val geocodedLocationsFlow = MutableStateFlow(emptyList<GeocodedLocationDto>())

    var updateLocationsJob: Job? = null

    fun updateQuery(query: String) {
        updateLocationsJob?.cancel()
        updateLocationsJob = viewModelScope.launch {
            // Get locations
            val geocodedLocations = try {
                geocodingApi.getGeocodedLocations(
                    name = query,
                    language = settings.data.last().queryLanguage.queryName
                )
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
            // Update if exists
            if (geocodedLocations?.results != null) {
                geocodedLocationsFlow.emit(geocodedLocations.results)
            }
        }
    }

    fun insertCurrentLocation() {
        insertLocation(
            item = GeocodedLocationItem(
                id = 0,
                name = "",
                latitude = 0.0,
                longitude = 0.0,
                admin1 = null,
                country = null,
                isAlreadyPresent = false
            )
        )
    }

    fun insertLocation(item: GeocodedLocationItem) {
        viewModelScope.launch {
            // Create the location object
            val location = Location(
                id = item.id,
                name = item.name,
                latitude = item.latitude,
                longitude = item.longitude,
                lastUpdate = LocalDateTime.MIN,
                sequence = (weatherDatabase.getLocationDao().getSequenceGroupByMaxSequence() ?: 0) + 1
            )
            // Insert into database
            weatherDatabase.getLocationDao().insert(location)
            // Force update
            val forceUpdateWorkRequest = OneTimeWorkRequestBuilder<UpdateForecastWorker>()
                .addTag(UpdateForecastWorker.TAG_ONE_TIME)
                .build()
            workManager.enqueue(forceUpdateWorkRequest)
        }
    }

    val geocodedLocationItems = combine(
        locationIdsFlow, geocodedLocationsFlow
    ) { locationIds, geocodedLocations ->
        geocodedLocations.map {
            GeocodedLocationItem(
                id = it.id,
                name = it.name,
                latitude = it.latitude,
                longitude = it.longitude,
                country = it.country,
                admin1 = it.admin1,
                isAlreadyPresent = locationIds.contains(it.id)
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val isCurrentLocationAlreadyPresent = locationIdsFlow
        .map { it.contains(0) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

}