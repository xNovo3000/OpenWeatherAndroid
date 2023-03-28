package io.github.xnovo3000.openweather.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import io.github.xnovo3000.openweather.ui.item.ManagedLocation
import io.github.xnovo3000.openweather.ui.item.ManagedLocationItem

@Composable
fun ManageLocationsContent(
    locations: List<ManagedLocationItem>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = paddingValues.calculateTopPadding(),
            bottom = paddingValues.calculateBottomPadding() + WindowInsets.navigationBars
                .asPaddingValues().calculateBottomPadding()
        )
    ) {
        items(
            items = locations,
            key = { it.id }
        ) {
            ManagedLocation(item = it)
        }
    }
}