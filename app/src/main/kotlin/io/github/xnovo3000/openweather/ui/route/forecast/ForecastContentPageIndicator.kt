package io.github.xnovo3000.openweather.ui.route.forecast

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@Composable
fun ForecastContentPageIndicator(
    modifier: Modifier,
    currentPage: Int,
    pageCount: Int
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .navigationBarsPadding()
    ) {
        repeat(pageCount) { page ->
            if (page == currentPage) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = MaterialTheme.colorScheme.secondary)
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
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
            ForecastContentPageIndicator(
                modifier = Modifier,
                currentPage = 2,
                pageCount = 5
            )
        }
    }
}