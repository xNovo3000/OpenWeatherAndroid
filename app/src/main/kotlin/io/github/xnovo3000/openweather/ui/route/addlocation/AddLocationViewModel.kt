package io.github.xnovo3000.openweather.ui.route.addlocation

import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.xnovo3000.openweather.api.retrofit.api.GeocodingApi
import io.github.xnovo3000.openweather.api.retrofit.dto.GeocodedLocationDto
import io.github.xnovo3000.openweather.background.worker.UpdateForecastWorker
import io.github.xnovo3000.openweather.data.datastore.WeatherSettings
import io.github.xnovo3000.openweather.data.model.QueryLanguage
import io.github.xnovo3000.openweather.data.room.WeatherDatabase
import io.github.xnovo3000.openweather.data.room.entity.Location
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val weatherDatabase: WeatherDatabase,
    private val weatherSettings: DataStore<WeatherSettings>,
    private val geocodingApi: GeocodingApi,
    private val workManager: WorkManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /* Query management system */

    val query = savedStateHandle.getStateFlow("query", "")

    private var queryJob: Job? = null

    fun search(newQuery: String) {
        // Search locations with that query (only if the string is not blank)
        queryJob?.cancel()
        queryJob = viewModelScope.launch {
            // Set query value
            savedStateHandle["query"] = newQuery
            // Invalid if query is blank
            if (newQuery.isBlank()) {
                geocodedLocationsFlow.emit(emptyList())
                return@launch
            }
            // Try to get geocoded locations
            val geocodedLocations = try {
                geocodingApi.getGeocodedLocations(
                    name = newQuery,
                    language = weatherSettings.data.first().queryLanguage.queryName
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            // Dispatch
            when (val results = geocodedLocations?.results) {
                null -> geocodedLocationsFlow.emit(emptyList())
                else -> geocodedLocationsFlow.emit(results)
            }
        }
    }

    /* Location item generation system */

    private val geocodedLocationsFlow = MutableStateFlow(emptyList<GeocodedLocationDto>())
    private val locationIdsFlow = weatherDatabase.getLocationDao().listenAll()
        .map { locationList -> locationList.map { it.id } }

    val isCurrentLocationPresent = locationIdsFlow.map { it.contains(0) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

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
                isPresent = locationIds.contains(it.id)
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    /* Location insertion system */

    fun addLocation(
        id: Long,
        name: String,
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            // Get last number of sequence
            val sequenceNumber = weatherDatabase.getLocationDao().getLeastFavoriteLocation()
            // Create the location
            val newLocation = Location(
                id = id,
                name = name,
                latitude = latitude,
                longitude = longitude,
                lastUpdate = LocalDateTime.MIN,
                sequence = (sequenceNumber ?: 0) + 1
            )
            // Add to the database
            weatherDatabase.getLocationDao().insert(newLocation)
            // Force update
            val forecastUpdateWorker = OneTimeWorkRequestBuilder<UpdateForecastWorker>()
                .setInputData(inputData = workDataOf("id" to id))
                .build()
            workManager.enqueue(forecastUpdateWorker)
        }
    }

    /* Language management system */

    val queryLanguageFlow = weatherSettings.data.map { it.queryLanguage }
        .stateIn(viewModelScope, SharingStarted.Eagerly, QueryLanguage.ENGLISH)

    fun changeQueryLanguage(newQueryLanguage: QueryLanguage) {
        viewModelScope.launch {
            // Update settings
            weatherSettings.updateData { it.copy(queryLanguage = newQueryLanguage) }
        }
    }

}