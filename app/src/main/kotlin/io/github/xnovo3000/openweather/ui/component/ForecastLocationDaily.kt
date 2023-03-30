package io.github.xnovo3000.openweather.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.data.model.WeatherCode
import io.github.xnovo3000.openweather.ui.item.ForecastLocationDay
import io.github.xnovo3000.openweather.ui.item.ForecastLocationDayItem
import io.github.xnovo3000.openweather.ui.core.WeatherTheme
import java.time.LocalDate

@Composable
fun ForecastLocationDaily(items: List<ForecastLocationDayItem>) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.forecast_location_daily_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        items.forEachIndexed { index, item ->
            if (index != 0) {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
            ForecastLocationDay(item = item)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    val now = LocalDate.now()
    WeatherTheme {
        Surface {
            ForecastLocationDaily(
                items = List(4) {
                    ForecastLocationDayItem(
                        date = now.plusDays(it + 0L),
                        weatherCode = WeatherCode.values()[it],
                        temperatureMin = 9,
                        temperatureMax = 21,
                        precipitationProbability = it + 20
                    )
                }
            )
        }
    }
}