package io.github.xnovo3000.openweather.ui.core

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable

val PaddingValues.withNavigationBarPadding: PaddingValues
    @Composable get() = PaddingValues(
        top = calculateTopPadding(),
        bottom = calculateBottomPadding() + WindowInsets.navigationBars
            .asPaddingValues().calculateBottomPadding()
    )