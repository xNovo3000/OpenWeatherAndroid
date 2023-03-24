package io.github.xnovo3000.openweather.ui.item

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WaterDrop
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
import io.github.xnovo3000.openweather.model.WeatherCode
import io.github.xnovo3000.openweather.ui.theme.WeatherTheme
import java.time.LocalDate
import java.time.ZoneId

data class ForecastLocationDayItem(
    val date: LocalDate,
    val weatherCode: WeatherCode,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val precipitationProbability: Int
)

@Composable
fun ForecastLocationDay(item: ForecastLocationDayItem) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 20.dp,
                vertical = 4.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = stringResource(
                id = R.string.forecast_location_day_date,
                item.date.atTime(0, 0)
                    .atZone(ZoneId.systemDefault()).toEpochSecond()
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(
                id = R.string.forecast_location_day_precipitation_probability,
                item.precipitationProbability
            ),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Rounded.WaterDrop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            modifier = Modifier.widthIn(min = 112.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = stringResource(
                    id = R.string.forecast_location_day_temperature,
                    item.temperatureMax, item.temperatureMin
                ),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(
                    id = item.weatherCode.getIcon(isNightTime = false)
                ),
                contentDescription = null
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
            ForecastLocationDay(
                item = ForecastLocationDayItem(
                    date = LocalDate.now(),
                    weatherCode = WeatherCode.PARTLY_CLOUDY,
                    temperatureMin = 12,
                    temperatureMax = 22,
                    precipitationProbability = 89
                )
            )
        }
    }
}