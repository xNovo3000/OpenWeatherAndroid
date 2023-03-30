package io.github.xnovo3000.openweather.ui.route.addlocation

import android.content.res.Configuration
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.xnovo3000.openweather.data.model.QueryLanguage
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@ExperimentalMaterial3Api
@Composable
fun QueryLanguageItemView(
    queryLanguage: QueryLanguage,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    ListItem(
        headlineText = {
            Text(text = stringResource(id = queryLanguage.stringRes))
        },
        leadingContent = {
            RadioButton(
                selected = isSelected,
                onClick = onSelected
            )
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
            QueryLanguageItemView(
                queryLanguage = QueryLanguage.ENGLISH,
                isSelected = false,
                onSelected = {}
            )
        }
    }
}