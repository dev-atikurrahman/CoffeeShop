package com.atik.coffeeshop.navigation.bottom_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.atik.coffeeshop.R
import com.atik.coffeeshop.navigation.Routes

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        BottomNavigationItem(
            title = stringResource(R.string.explore),
            route = Routes.Explore.route,
            selectedIcon = Icons.Rounded.Explore,
            unSelectedIcon = Icons.Outlined.Explore,
        ),
        BottomNavigationItem(
            title = stringResource(R.string.cart),
            route = Routes.Cart.route,
            selectedIcon = Icons.Rounded.ShoppingCart,
            unSelectedIcon = Icons.Outlined.ShoppingCart,
            badgeCount = 4
        ),
        BottomNavigationItem(
            title = stringResource(R.string.favorite),
            route = Routes.Favorite.route,
            selectedIcon = Icons.Rounded.Favorite,
            unSelectedIcon = Icons.Outlined.FavoriteBorder,
            // badgeCount = sharedViewModel.favoriteItems.size.takeIf { it > 0}
        ),
        BottomNavigationItem(
            title = stringResource(R.string.profile),
            route = Routes.Profile.route,
            selectedIcon = Icons.Rounded.Person,
            unSelectedIcon = Icons.Outlined.Person,
        )

    )

    val cs = MaterialTheme.colorScheme

    val navigationItemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = cs.onBackground,
        selectedTextColor = cs.onSecondaryContainer,
        unselectedIconColor = cs.onSecondaryContainer.copy(alpha = 0.7f),
        unselectedTextColor = cs.onSecondaryContainer.copy(alpha = 0.7f),
        indicatorColor = cs.secondaryContainer,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 28.dp,
                    topEnd = 28.dp
                )
            ),
        containerColor = cs.surfaceContainerHigh,
        tonalElevation = 0.dp,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { navController.navigateToBottomBar(item.route) },
                label = { Text(text = item.title) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge { Text(text = item.badgeCount.toString()) }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    }
                },
                colors = navigationItemColors


            )
        }
    }

}


