package io.github.xnovo3000.openweather.ui.item

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.theme.WeatherTheme

data class GeocodedLocationItem(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val admin1: String?,
    val isAlreadyPresent: Boolean
)

@ExperimentalMaterial3Api
@Composable
fun GeocodedLocation(
    item: GeocodedLocationItem,
    onAddClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(text = item.name)
        },
        supportingText = {
            Text(
                text = stringResource(
                    id = R.string.find_location_item_trailing,
                    item.country ?: stringResource(
                        id = R.string.find_location_item_trailing_null
                    ),
                    item.admin1 ?: stringResource(
                        id = R.string.find_location_item_trailing_null
                    )
                )
            )
        },
        trailingContent = {
            FilledIconButton(
                onClick = onAddClick,
                enabled = !item.isAlreadyPresent
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            GeocodedLocation(
                item = GeocodedLocationItem(
                    id = 1,
                    name = "Massafra",
                    latitude = 42.15,
                    longitude = 13.10,
                    country = "Italia",
                    admin1 = "Puglia",
                    isAlreadyPresent = true
                )
            ) {}
        }
    }
}