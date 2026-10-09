package com.atik.coffeeshop.navigation.nav_graph

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atik.coffeeshop.R
import com.atik.coffeeshop.features.auth.presentation.login.LoginViewModel.Companion.AUTH_TAG
import com.atik.coffeeshop.features.explore.presentation.SharedViewModel
import com.atik.coffeeshop.features.onboarding.presentation.OnboardingScreen
import com.atik.coffeeshop.navigation.AUTH_GRAPH_ROUTE
import com.atik.coffeeshop.navigation.HOME_GRAPH_ROUTE
import com.atik.coffeeshop.navigation.Routes
import com.atik.coffeeshop.navigation.SplashViewModel
import com.atik.coffeeshop.navigation.StartDestination
import com.atik.coffeeshop.navigation.bottom_bar.BottomNavigationBar
import com.atik.coffeeshop.ui.components.AppLoadingIndicator
import org.koin.androidx.compose.koinViewModel

private const val SPLASH_ROOT_ROUTE = "splash_root"

@Composable
fun SplashNavHost(
    splashViewModel: SplashViewModel = koinViewModel()
) {
    val startDestination by splashViewModel.startDestination.collectAsStateWithLifecycle()
    var resolvedStart by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(startDestination) {
        if (resolvedStart == null && startDestination != StartDestination.Loading) {
            resolvedStart = when (startDestination) {
                StartDestination.Onboarding -> Routes.Onboarding.route
                StartDestination.Login -> AUTH_GRAPH_ROUTE
                StartDestination.Home -> HOME_GRAPH_ROUTE
                StartDestination.Loading -> Routes.Onboarding.route //
            }
            Log.d(
                AUTH_TAG,
                "SplashNavHost: recomposed with destination=$startDestination, actualStart=$resolvedStart"
            )
        }
    }
    val currentResolveStart = resolvedStart

    if (currentResolveStart == null) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AppLoadingIndicator(size = 32.dp)
        }
    } else {
        val navController = rememberNavController()
        val sharedViewModel: SharedViewModel = koinViewModel()

        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route
        val showBottomBar = currentRoute in listOf(
            Routes.Explore.route,
            Routes.Cart.route,
            Routes.Favorite.route,
            Routes.Profile.route
        )


        SharedTransitionLayout {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomBarSection(
                        showBottomBar = showBottomBar,
                        navController = navController
                    )
                },
                contentWindowInsets = WindowInsets.safeDrawing,
                containerColor = colorResource(R.color.white)
            ) {innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = currentResolveStart,
                    route = SPLASH_ROOT_ROUTE,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Routes.Onboarding.route) {
                        OnboardingScreen(
                            onGetStarted = {
                                navController.navigate(Routes.Login.route) {
                                    popUpTo(Routes.Onboarding.route) { inclusive = true }
                                }
                            }
                        )
                    }
                    authNavGraph(navController = navController)

                    homeNavGraph(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        sharedTransitionScope = this@SharedTransitionLayout
                    )

                }
            }
        }

    }

}


@Composable
private fun BottomBarSection(
    showBottomBar: Boolean, navController: NavController
) {
    AnimatedVisibility(
        visible = showBottomBar, enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(300)), exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(200))
    ) {
        BottomNavigationBar(navController = navController)
    }
}
