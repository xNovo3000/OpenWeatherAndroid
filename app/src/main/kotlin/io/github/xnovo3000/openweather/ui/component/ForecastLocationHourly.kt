package io.github.xnovo3000.openweather.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.data.model.WeatherCode
import io.github.xnovo3000.openweather.data.model.WindDirection
import io.github.xnovo3000.openweather.data.model.WindSpeedUnit
import io.github.xnovo3000.openweather.ui.item.ForecastLocationHour
import io.github.xnovo3000.openweather.ui.item.ForecastLocationHourItem
import io.github.xnovo3000.openweather.ui.core.WeatherTheme
import java.time.LocalDate

@Composable
fun ForecastLocationHourly(items: List<ForecastLocationHourItem>) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.forecast_location_hourly_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow {
            items(
                items = items,
                key = { it.dateTime.hour }
            ) {
                ForecastLocationHour(item = it)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    val now = LocalDate.now().atTime(0, 0)
    WeatherTheme {
        Surface {
            ForecastLocationHourly(
                items = List(24) {
                    ForecastLocationHourItem(
                        dateTime = now.plusHours(it + 0L),
                        temperature = 20,
                        weatherCode = WeatherCode.PARTLY_CLOUDY,
                        windSpeed = 8,
                        windDirection = WindDirection.EAST,
                        windSpeedUnit = WindSpeedUnit.KMH,
                        isNightTime = it < 6 || it > 18
                    )
                }
            )
        }
    }
}