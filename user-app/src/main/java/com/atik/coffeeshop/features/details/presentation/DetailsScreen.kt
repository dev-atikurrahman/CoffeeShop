package com.atik.coffeeshop.features.details.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.atik.coffeeshop.features.details.presentation.widget.AddToCartAndPriceSection
import com.atik.coffeeshop.features.details.presentation.widget.CoffeeSizeSelectorSection
import com.atik.coffeeshop.features.details.presentation.widget.DetailsImageSection
import com.atik.coffeeshop.features.details.presentation.widget.DetailsTopBar
import com.atik.coffeeshop.features.details.presentation.widget.QuantityAndDetailsSection
import com.atik.coffeeshop.features.home.explore.data.models.ItemsModel
import com.atik.coffeeshop.features.details.presentation.DetailsViewModel


@Composable
fun SharedTransitionScope.DetailsScreen(
    item: ItemsModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackClick: () -> Unit,
    viewModel: DetailsViewModel = viewModel()
) {
    val selectedSize by viewModel.selectedSize.collectAsStateWithLifecycle()
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    )
    {

        DetailsTopBar(
            isItemFavorite = false,
            onBackClick = onBackClick,
            onFavoriteClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            DetailsImageSection(
                item = item,
                animatedVisibilityScope = animatedVisibilityScope
            )

            Spacer(modifier = Modifier.height(18.dp))
            CoffeeSizeSelectorSection(
                selectedSize = selectedSize,
                onSizeSelected = viewModel::onSizeSelected
            )

            Spacer(modifier = Modifier.height(24.dp))
            QuantityAndDetailsSection(item = item)
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(1000)),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(animationSpec = tween(1000))
        ) {
            AddToCartAndPriceSection(
                item = item
            )
        }


    }


}