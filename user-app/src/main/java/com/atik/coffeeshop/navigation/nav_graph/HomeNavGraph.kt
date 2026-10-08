package com.atik.coffeeshop.navigation.nav_graph

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.atik.coffeeshop.core.defaultEnterTransition
import com.atik.coffeeshop.core.defaultExitTransition
import com.atik.coffeeshop.features.details.presentation.DetailsScreen
import com.atik.coffeeshop.features.edit_profile.presentation.EditProfileScreen
import com.atik.coffeeshop.features.home.cart.presentation.CartScreen
import com.atik.coffeeshop.features.home.explore.presentation.ExploreScreen
import com.atik.coffeeshop.features.home.explore.presentation.SharedViewModel
import com.atik.coffeeshop.features.home.favorite.presentation.FavoriteScreen
import com.atik.coffeeshop.features.home.profile.domain.model.ProfileDestination
import com.atik.coffeeshop.features.home.profile.presentation.ProfileRoot
import com.atik.coffeeshop.features.location.presentation.LocationScreen
import com.atik.coffeeshop.features.order_history.presentation.OrderHistoryScreen
import com.atik.coffeeshop.features.payment.presentation.PaymentScreen
import com.atik.coffeeshop.features.settings.presentation.ProfileSettingsScreen
import com.atik.coffeeshop.navigation.AUTH_GRAPH_ROUTE
import com.atik.coffeeshop.navigation.HOME_GRAPH_ROUTE
import com.atik.coffeeshop.navigation.Routes

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    sharedTransitionScope: SharedTransitionScope
) {
    navigation(startDestination = Routes.Explore.route, route = HOME_GRAPH_ROUTE) {
        composable(
            route = Routes.Explore.route,
            enterTransition = { fadeIn(animationSpec = tween(250)) }
        ) {
            with(sharedTransitionScope) {
                ExploreScreen(
                    navController = navController,
                    onItemClick = { item ->
                        sharedViewModel.selectItem(item)
                        navController.navigate(Routes.Details.route)
                    }, animatedVisibilityScope = this@composable
                )
            }
        }

        composable(Routes.Cart.route) { CartScreen() }
        composable(Routes.Favorite.route) { FavoriteScreen() }

        composable(Routes.Profile.route) {
            ProfileRoot(
                onNavigate = { destination ->
                    val route = when (destination) {
                        ProfileDestination.Settings -> Routes.Settings.route
                        ProfileDestination.Location -> Routes.Location.route
                        ProfileDestination.Payment -> Routes.Payment.route
                        ProfileDestination.History -> Routes.History.route
                        ProfileDestination.Notification -> Routes.Notification.route
                        ProfileDestination.EditProfile -> Routes.EditProfile.route
                    }
                    navController.navigate(route)
                },
                onLoggedOut = {
                    navController.navigate(AUTH_GRAPH_ROUTE) {
                        popUpTo(HOME_GRAPH_ROUTE) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Routes.EditProfile.route) {
            EditProfileScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Routes.Settings.route) {
            ProfileSettingsScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Routes.Location.route) {
            LocationScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Routes.Payment.route) {
            PaymentScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Routes.History.route) {
            OrderHistoryScreen(onBackClick = { navController.popBackStack() })
        }
        composable(route = Routes.Notification.route) {
            ProfileSettingsScreen(onBackClick = { navController.popBackStack() })
        }

        composable(
            route = Routes.Details.route,
            enterTransition = defaultEnterTransition,
            exitTransition = defaultExitTransition,
            popEnterTransition = defaultEnterTransition,
            popExitTransition = defaultExitTransition
        ) {
            val item = sharedViewModel.selectedItem

            if (item != null) {
                with(sharedTransitionScope) {
                    DetailsScreen(
                        item = item,
                        onBackClick = { navController.popBackStack() },
                        animatedVisibilityScope = this@composable
                    )
                }
            } else {
                LaunchedEffect(Unit) { navController.popBackStack() }
            }
        }
    }
}