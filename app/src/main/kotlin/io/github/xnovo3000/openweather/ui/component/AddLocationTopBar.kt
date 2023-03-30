package io.github.xnovo3000.openweather.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.core.WeatherTheme

@ExperimentalMaterial3Api
@Composable
fun AddLocationTopBar(
    onSearchClick: (String) -> Unit,
    focusRequester: FocusRequester,
    onNavigationIconClick: () -> Unit,
    onChangeLanguageClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        title = {
            // Query state
            var query by remember { mutableStateOf("") }
            // Build
            if (query.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.add_location_top_bar_title),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            BasicTextField(
                modifier = Modifier.focusRequester(focusRequester),
                value = query,
                onValueChange = { query = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick(query) }
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = LocalContentColor.current
                ),
                cursorBrush = SolidColor(LocalContentColor.current)
            )
        },
        actions = {
            IconButton(onClick = onChangeLanguageClick) {
                Icon(
                    imageVector = Icons.Rounded.Language,
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalMaterial3Api
@Composable
private fun Preview() {
    WeatherTheme {
        AddLocationTopBar(
            onSearchClick = {},
            focusRequester = FocusRequester(),
            onNavigationIconClick = {},
            onChangeLanguageClick = {},
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}