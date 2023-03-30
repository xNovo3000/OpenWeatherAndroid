package io.github.xnovo3000.openweather.ui.route.forecast

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@Composable
fun ForecastContentEmpty(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.forecast_2_content_empty_rationale),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            ForecastContentEmpty(paddingValues = PaddingValues(top = 88.dp))
        }
    }
}