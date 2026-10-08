package com.atik.coffeeshop.features.explore.presentation


import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.atik.coffeeshop.features.explore.data.datasources.DummyData.dummyBanners
import com.atik.coffeeshop.features.explore.data.datasources.DummyData.dummyCategories
import com.atik.coffeeshop.features.explore.data.datasources.DummyData.dummyCopyList
import com.atik.coffeeshop.features.explore.data.models.ItemsModel
import com.atik.coffeeshop.features.explore.presentation.widgets.BannerImageSection
import com.atik.coffeeshop.features.explore.presentation.widgets.CategorySection
import com.atik.coffeeshop.features.explore.presentation.widgets.ExploreTopBar
import com.atik.coffeeshop.features.explore.presentation.widgets.PopularCopySection
import com.atik.coffeeshop.features.explore.presentation.widgets.SearchBarSection

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SharedTransitionScope.ExploreScreen(
    navController: NavController,
    onItemClick: (ItemsModel) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(bottom = 200.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        ExploreTopBar(
            userName = "Tina",
            onNotificationClick = {},
        )

        Spacer(modifier = Modifier.height(18.dp))
        SearchBarSection()

        Spacer(modifier = Modifier.height(18.dp))
        CategorySection(categories = dummyCategories)

        Spacer(modifier = Modifier.height(10.dp))
        BannerImageSection(images = dummyBanners)

        Spacer(modifier = Modifier.height(10.dp))
        PopularCopySection(
            popularCopies = dummyCopyList,
            onItemClick = onItemClick,
            animatedVisibilityScope = animatedVisibilityScope
        )
    }


}