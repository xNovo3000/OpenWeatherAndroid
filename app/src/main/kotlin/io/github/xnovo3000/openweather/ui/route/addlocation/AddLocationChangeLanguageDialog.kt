package io.github.xnovo3000.openweather.ui.route.addlocation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.data.model.QueryLanguage
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@ExperimentalMaterial3Api
@Composable
fun AddLocationChangeLanguageDialog(
    selectedLanguage: QueryLanguage?,
    onLanguageSelect: (QueryLanguage) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = stringResource(id = R.string.add_location_dialog_title))
        },
        text = {
            Column {
                Divider()
                Column(
                    modifier = Modifier
                        .heightIn(max = 320.dp)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    QueryLanguage.values().forEach { queryLanguage ->
                        QueryLanguageItemView(
                            queryLanguage = queryLanguage,
                            isSelected = queryLanguage == selectedLanguage,
                            onSelected = { onLanguageSelect(queryLanguage) }
                        )
                    }
                }
                Divider()
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.add_location_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(id = R.string.add_location_dialog_dismiss))
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
            AddLocationChangeLanguageDialog(
                selectedLanguage = QueryLanguage.ENGLISH,
                onLanguageSelect = {},
                onDismissRequest = {},
                onConfirm = {}
            )
        }
    }
}