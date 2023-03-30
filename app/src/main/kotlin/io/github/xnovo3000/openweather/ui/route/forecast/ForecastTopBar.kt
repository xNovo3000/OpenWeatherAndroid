package io.github.xnovo3000.openweather.ui.route.forecast

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@ExperimentalMaterial3Api
@Composable
fun ForecastTopBar(
    onSearchClick: () -> Unit,
    onManageLocationsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    // Get status bar scrim
    val statusBarColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8F)
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    // Build top bar
    Column(modifier = Modifier.fillMaxWidth()) {
        // Status bar scrim
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(statusBarHeight)
                .background(statusBarColor)
        )
        // App bar
        ElevatedCard(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                )
                .fillMaxWidth()
                .heightIn(min = 52.dp),
            onClick = onSearchClick,
            shape = RoundedCornerShape(26.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
                    .heightIn(min = 52.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp),
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = R.string.forecast_2_top_bar_headline),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(onClick = onManageLocationsClick) {
                    Icon(
                        imageVector = Icons.Rounded.LocationCity,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                // Dropdown state
                var expanded by remember { mutableStateOf(false) }
                // Dropdown
                Box {
                    // Three dots button
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    // Dropdown
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(
                                        id = R.string.forecast_2_top_bar_action_settings
                                    )
                                )
                            },
                            onClick = {
                                onSettingsClick()
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(
                                        id = R.string.forecast_2_top_bar_action_info
                                    )
                                )
                            },
                            onClick = {
                                onInfoClick()
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            ForecastTopBar(
                onSearchClick = {},
                onManageLocationsClick = {},
                onSettingsClick = {},
                onInfoClick = {}
            )
        }
    }
}