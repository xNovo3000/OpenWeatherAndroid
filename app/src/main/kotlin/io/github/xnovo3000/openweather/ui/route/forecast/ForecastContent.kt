package io.github.xnovo3000.openweather.ui.route.forecast

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@ExperimentalFoundationApi
@Composable
fun ForecastContent(
    items: List<Int>,  // TODO: Add real things
    paddingValues: PaddingValues
) {
    Crossfade(targetState = items.size) { totalPages ->
        if (totalPages == 0) {
            ForecastContentEmpty(paddingValues = paddingValues)
        } else {
            // Handle the pager state
            val pagerState = rememberPagerState()
            // Build the content
            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    pageCount = items.size,
                ) {}
                ForecastContentPageIndicator(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    currentPage = pagerState.currentPage,
                    pageCount = items.size
                )
            }
        }
    }

}