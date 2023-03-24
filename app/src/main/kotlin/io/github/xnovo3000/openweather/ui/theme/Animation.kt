package io.github.xnovo3000.openweather.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

@ExperimentalAnimationApi
val EnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(),
        initialOffset = { it / 8 }
    ) + fadeIn(
        animationSpec = tween()
    )
}

@ExperimentalAnimationApi
val ExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Left,
        animationSpec = tween(),
        targetOffset = { it / 8 }
    ) + fadeOut(
        animationSpec = tween()
    )
}

@ExperimentalAnimationApi
val PopEnterTransition: AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(),
        initialOffset = { it / 8 }
    ) + fadeIn(
        animationSpec = tween()
    )
}

@ExperimentalAnimationApi
val PopExitTransition: AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        towards = AnimatedContentScope.SlideDirection.Right,
        animationSpec = tween(),
        targetOffset = { it / 8 }
    ) + fadeOut(
        animationSpec = tween()
    )
}