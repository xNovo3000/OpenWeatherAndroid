package io.github.xnovo3000.openweather.api.retrofit.api

import io.github.xnovo3000.openweather.data.model.QueryLanguage
import io.github.xnovo3000.openweather.api.retrofit.dto.GeocodedLocationsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApi {

    @GET("search")
    suspend fun getGeocodedLocations(
        @Query("name") name: String,
        @Query("language") language: String = QueryLanguage.ENGLISH.queryName,
        @Query("count") count: Int = 25
    ): GeocodedLocationsDto?

}