package com.atik.coffeeshop.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atik.coffeeshop.R
import com.atik.coffeeshop.features.explore.presentation.SharedViewModel
import com.atik.coffeeshop.navigation.bottom_bar.BottomNavigationBar
import com.atik.coffeeshop.navigation.nav_graph.authNavGraph
import com.atik.coffeeshop.navigation.nav_graph.homeNavGraph
import org.koin.androidx.compose.koinViewModel

@Composable
fun CoffeeShopAppNavigation() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = koinViewModel() // koinViewModel() or viewModel()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoutes = currentBackStackEntry?.destination?.route

    val showBottomBar = currentRoutes in listOf(
        Routes.Explore.route,
        Routes.Cart.route,
        Routes.Favorite.route,
        Routes.Profile.route
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
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
                // its status color
                containerColor = colorResource(R.color.lightCream)
            ) { innerPadding ->

                NavHost(
                    navController = navController,
                    startDestination = AUTH_GRAPH_ROUTE,
                    route = ROOT_GRAPH_ROUTE,
                    modifier = Modifier.padding(innerPadding)
                ) {
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
    showBottomBar: Boolean,
    navController: NavController
) {
    AnimatedVisibility(
        visible = showBottomBar,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(200))
    ) {
        BottomNavigationBar(navController = navController)
    }
}


