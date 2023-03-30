package io.github.xnovo3000.openweather.ui.route.addlocation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun AddLocationContent(
    isCurrentLocationPresent: Boolean,
    onAddCurrentLocationClick: () -> Unit,
    geocodedLocationItemList: List<GeocodedLocationItem>,
    onAddLocationClick: (GeocodedLocationItem) -> Unit,
    paddingValues: PaddingValues
) {
    LazyColumn(contentPadding = paddingValues) {
        // Current location
        item(key = 0) {
            CurrentLocationItemView(
                isPresent = isCurrentLocationPresent,
                onAddClick = onAddCurrentLocationClick
            )
        }
        // Other locations
        items(
            items = geocodedLocationItemList,
            key = { it.id }
        ) { geocodedLocationItem ->
            GeocodedLocationItemView(
                item = geocodedLocationItem,
                onAddClick = { onAddLocationClick(geocodedLocationItem) }
            )
        }
    }
}