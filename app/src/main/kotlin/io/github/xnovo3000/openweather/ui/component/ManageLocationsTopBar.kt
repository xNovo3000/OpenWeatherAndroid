package io.github.xnovo3000.openweather.ui.component

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.xnovo3000.openweather.R
import io.github.xnovo3000.openweather.ui.theme.WeatherTheme

@ExperimentalMaterial3Api
@Composable
fun ManageLocationsTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.manage_locations_title))
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
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
        ManageLocationsTopBar(
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            onNavigationIconClick = {}
        )
    }
}