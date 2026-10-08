package com.atik.coffeeshop.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavBackStackEntry


val sharedBoundsTransform = BoundsTransform { _, _ ->
    tween(
        durationMillis = 600, easing = FastOutSlowInEasing
    )
}

val defaultEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition =
    {
        fadeIn(animationSpec = tween(durationMillis = 200))
    }

val defaultExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition =
    {
        fadeOut(animationSpec = tween(durationMillis = 150))
    }

@Composable
fun HorizontalSpacer(size: Dp) {
    Spacer(modifier = Modifier.width(size))
}

@Composable
fun VerticalSpacer(size: Dp) {
    Spacer(modifier = Modifier.height(size))
}