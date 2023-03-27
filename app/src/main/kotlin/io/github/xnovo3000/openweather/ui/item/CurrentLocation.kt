package io.github.xnovo3000.openweather.ui.item

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.xnovo3000.openweather.R

@ExperimentalMaterial3Api
@Composable
fun CurrentLocation(
    isAlreadyPresent: Boolean,
    onAddClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(text = stringResource(id = R.string.find_location_current))
        },
        trailingContent = {
            FilledTonalButton(
                onClick = onAddClick,
                enabled = isAlreadyPresent
            ) {
                Text(text = stringResource(R.string.find_location_item_add))
            }
        }
    )
}