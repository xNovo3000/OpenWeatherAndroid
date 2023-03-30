package io.github.xnovo3000.openweather.ui.route.forecast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@ExperimentalMaterialApi
@Composable
fun ForecastContentLocation(
    location: Int,
    onRefresh: () -> Unit,
    paddingValues: PaddingValues
) {
    // Manage state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        onRefresh = onRefresh,
        refreshThreshold = PullRefreshDefaults.RefreshThreshold + paddingValues.calculateTopPadding(),
        refreshingOffset = PullRefreshDefaults.RefreshingOffset + paddingValues.calculateTopPadding()
    )
    // Build location
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        // Build content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            // Top bar spacing
            Spacer(modifier = Modifier.padding(paddingValues.calculateTopPadding()))
            // TODO: Add content
            // Bottom bar spacing
            Spacer(modifier = Modifier.padding(paddingValues.calculateBottomPadding()))
        }
        // Build indicator
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = false,
            state = pullRefreshState
        )
    }
}