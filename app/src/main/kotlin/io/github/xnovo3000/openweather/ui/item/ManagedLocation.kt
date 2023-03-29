package io.github.xnovo3000.openweather.ui.item

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.model.TemperatureUnit
import io.github.xnovo3000.openweather.model.WeatherCode
import io.github.xnovo3000.openweather.model.getIcon
import io.github.xnovo3000.openweather.ui.theme.WeatherTheme
import java.time.LocalDateTime

data class ManagedLocationItem(
    val id: Long,
    val name: String,
    val lastUpdate: LocalDateTime,
    val temperature: Int?,
    val weatherCode: WeatherCode?,
    val isNight: Boolean?,
    val temperatureUnit: TemperatureUnit
)

@Composable
fun ManagedLocation(item: ManagedLocationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1F)) {
                Text(
                    text = item.name.ifBlank {
                        stringResource(id = R.string.manage_locations_current_position)
                    },
                    style = MaterialTheme.typography.headlineSmall
                )
                Row {
                    Text(
                        text = "${item.temperature ?: "--"}",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = stringResource(id = item.temperatureUnit.stringRes),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            if (item.weatherCode != null && item.isNight != null) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(
                        id = item.weatherCode.getIcon(
                            isNightTime = item.isNight
                        )
                    ),
                    contentDescription = null
                )
            } else {
                Icon(
                    modifier = Modifier.size(64.dp),
                    imageVector = Icons.Rounded.Remove,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            ManagedLocation(
                item = ManagedLocationItem(
                    id = 1,
                    name = "New York",
                    lastUpdate = LocalDateTime.now(),
                    temperature = 21,
                    weatherCode = WeatherCode.PARTLY_CLOUDY,
                    isNight = false,
                    temperatureUnit = TemperatureUnit.CELSIUS,
                )
            )
        }
    }
}