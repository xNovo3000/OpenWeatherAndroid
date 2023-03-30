package io.github.xnovo3000.openweather.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EditLocationAlt
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.data.model.WeatherCode
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

data class DrawerLocationItem(
    val id: Long,
    val name: String,
    val temperature: Int?,
    val weatherCode: WeatherCode?,
    val isNightTime: Boolean?
)

@ExperimentalMaterial3Api
@Composable
fun ForecastDrawer(
    selectedLocationId: Long,
    items: List<DrawerLocationItem>,
    onItemClick: (DrawerLocationItem) -> Unit,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    ModalDrawerSheet {
        // Heading
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = rememberDrawablePainter(
                    drawable = ContextCompat.getDrawable(
                        LocalContext.current,
                        R.mipmap.ic_launcher
                    )
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.forecast_drawer_title),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        // Locations
        if (items.isNotEmpty()) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
                    .padding(
                        horizontal = 28.dp,
                        vertical = 4.dp
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(id = R.string.forecast_drawer_locations),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            // All locations
            items.forEach {
                NavigationDrawerItem(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    label = {
                        Text(
                            text = it.name.ifBlank {
                                stringResource(id = R.string.forecast_drawer_current_position)
                            },
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    icon = {
                        if (it.weatherCode != null && it.isNightTime != null) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    id = it.weatherCode.getIcon(isNightTime = it.isNightTime)
                                ),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.Remove,
                                contentDescription = null
                            )
                        }
                    },
                    badge = {
                        Text(
                            text = if (it.temperature == null) {
                                stringResource(id = R.string.forecast_drawer_temperature_unknown)
                            } else {
                                stringResource(id = R.string.forecast_drawer_temperature, it.temperature)
                            },
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    selected = it.id == selectedLocationId,
                    onClick = { onItemClick(it) }
                )
            }
            // Divider below
            Divider(modifier = Modifier.padding(horizontal = 28.dp))
        }
        // More header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .padding(
                    horizontal = 28.dp,
                    vertical = 4.dp
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.forecast_drawer_more),
                style = MaterialTheme.typography.titleSmall
            )
        }
        // Manage locations
        NavigationDrawerItem(
            modifier = Modifier.padding(horizontal = 12.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.forecast_drawer_manage_locations),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.EditLocationAlt,
                    contentDescription = null
                )
            },
            onClick = onManageLocationsClick,
            selected = false
        )
        // Settings
        NavigationDrawerItem(
            modifier = Modifier.padding(horizontal = 12.dp),
            label = {
                Text(
                    text = stringResource(id = R.string.forecast_drawer_settings),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = null
                )
            },
            onClick = onSettingsClick,
            selected = false
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    WeatherTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ForecastDrawer(
                selectedLocationId = 0,
                items = List(5) {
                    DrawerLocationItem(
                        id = it + 1L,
                        name = if (it == 1) "" else "Location $it",
                        temperature = if (it == 4) null else 20 + it,
                        weatherCode = if (it == 4) null else WeatherCode.values()[it],
                        isNightTime = it % 2 == 0
                    )
                },
                onItemClick = {},
                onManageLocationsClick = {},
                onSettingsClick = {}
            )
        }
    }
}