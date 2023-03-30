package io.github.xnovo3000.openweather.ui.item

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.data.model.WeatherCode
import io.github.xnovo3000.openweather.data.model.WindDirection
import io.github.xnovo3000.openweather.data.model.WindSpeedUnit
import io.github.xnovo3000.openweather.ui.core.WeatherTheme
import java.time.LocalDateTime
import java.time.ZoneId

data class ForecastLocationHourItem(
    val dateTime: LocalDateTime,
    val temperature: Int,
    val weatherCode: WeatherCode,
    val windSpeed: Int,
    val windDirection: WindDirection,
    val windSpeedUnit: WindSpeedUnit,
    val isNightTime: Boolean
)

@Composable
fun ForecastLocationHour(item: ForecastLocationHourItem) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                id = R.string.forecast_location_hour_time,
                item.dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(2.dp))
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(
                id = item.weatherCode.getIcon(isNightTime = item.isNightTime)
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = stringResource(
                id = R.string.forecast_location_hour_temperature,
                item.temperature
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(1.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(
                    id = item.windDirection.icon
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(
                    id = R.string.forecast_location_hour_wind,
                    item.windSpeed, stringResource(
                        id = item.windSpeedUnit.stringRes
                    )
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            ForecastLocationHour(
                item = ForecastLocationHourItem(
                    dateTime = LocalDateTime.now(),
                    temperature = 15,
                    weatherCode = WeatherCode.PARTLY_CLOUDY,
                    windSpeed = 6,
                    windDirection = WindDirection.WEST,
                    windSpeedUnit = WindSpeedUnit.KMH,
                    isNightTime = false
                )
            )
        }
    }
}