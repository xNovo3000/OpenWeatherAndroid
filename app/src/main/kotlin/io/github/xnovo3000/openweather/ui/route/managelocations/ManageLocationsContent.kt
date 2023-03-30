package io.github.xnovo3000.openweather.ui.route.managelocations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ManageLocationsContent(
    locations: List<ManagedLocationItem>,
    paddingValues: PaddingValues
) {
    LazyColumn(contentPadding = paddingValues) {
        items(
            items = locations,
            key = { it.id }
        ) {
            ManagedLocationItemView(item = it)
        }
    }
}