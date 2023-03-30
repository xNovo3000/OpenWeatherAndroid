package io.github.xnovo3000.openweather.ui.route.addlocation

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@ExperimentalMaterial3Api
@Composable
fun CurrentLocationItemView(
    isPresent: Boolean,
    onAddClick: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(text = stringResource(id = R.string.find_location_current))
        },
        trailingContent = {
            FilledTonalIconButton(
                onClick = onAddClick,
                enabled = !isPresent
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            CurrentLocationItemView(isPresent = false) {}
        }
    }
}