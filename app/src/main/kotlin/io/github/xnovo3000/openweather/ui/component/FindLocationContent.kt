package io.github.xnovo3000.openweather.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import io.github.xnovo3000.openweather.ui.item.CurrentLocation
import io.github.xnovo3000.openweather.ui.item.GeocodedLocation
import io.github.xnovo3000.openweather.ui.item.GeocodedLocationItem

@ExperimentalMaterial3Api
@Composable
fun FindLocationContent(
    geocodedLocations: List<GeocodedLocationItem>,
    isCurrentLocationAlreadyPresent: Boolean,
    onAddCurrentLocationClick: () -> Unit,
    onAddLocationClick: (GeocodedLocationItem) -> Unit,
    paddingValues: PaddingValues
) {
    // Build
    LazyColumn(
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding() + WindowInsets.navigationBars
                .asPaddingValues().calculateBottomPadding()
        )
    ) {
        // Build current location
        item(key = 0) {
            CurrentLocation(
                isAlreadyPresent = isCurrentLocationAlreadyPresent,
                onAddClick = onAddCurrentLocationClick
            )
        }
        // Build locations
        items(
            items = geocodedLocations,
            key = { it.id }
        ) {
            GeocodedLocation(
                item = it,
                onAddClick = { onAddLocationClick(it) }
            )
        }
    }
}