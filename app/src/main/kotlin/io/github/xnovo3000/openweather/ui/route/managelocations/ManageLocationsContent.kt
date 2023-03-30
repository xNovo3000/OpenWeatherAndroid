package io.github.xnovo3000.openweather.ui.route.managelocations

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import io.github.xnovo3000.openweather.ui.core.withNavigationBarPadding

@Composable
fun ManageLocationsContent(
    locations: List<ManagedLocationItem>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        contentPadding = paddingValues.withNavigationBarPadding
    ) {
        items(
            items = locations,
            key = { it.id }
        ) {
            ManagedLocation(item = it)
        }
    }
}