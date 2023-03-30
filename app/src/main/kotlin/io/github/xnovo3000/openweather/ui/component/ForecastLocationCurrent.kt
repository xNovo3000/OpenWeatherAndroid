package io.github.xnovo3000.openweather.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.data.model.TemperatureUnit
import io.github.xnovo3000.openweather.data.model.WeatherCode
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

data class ForecastLocationCurrentItem(
    val temperature: Int,
    val temperatureUnit: TemperatureUnit,
    val weatherCode: WeatherCode,
    val isNightTime: Boolean
)

@Composable
fun ForecastLocationCurrent(item: ForecastLocationCurrentItem) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1F)
        ) {
            Row {
                Text(
                    text = "${item.temperature}",
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(id = item.temperatureUnit.stringRes),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = stringResource(
                    id = item.weatherCode.displayName
                ),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            modifier = Modifier.size(88.dp),
            painter = painterResource(
                id = item.weatherCode.getIcon(isNightTime = item.isNightTime)
            ),
            contentDescription = null
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            ForecastLocationCurrent(
                item = ForecastLocationCurrentItem(
                    temperature = 21,
                    temperatureUnit = TemperatureUnit.CELSIUS,
                    weatherCode = WeatherCode.PARTLY_CLOUDY,
                    isNightTime = false
                )
            )
        }
    }
}