package io.github.xnovo3000.openweather.api.retrofit.dto

data class GeocodedLocationDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val admin1: String?
)