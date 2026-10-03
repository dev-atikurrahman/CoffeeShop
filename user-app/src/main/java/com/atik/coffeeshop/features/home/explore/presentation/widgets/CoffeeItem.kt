package com.atik.coffeeshop.features.home.explore.presentation.widgets

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atik.coffeeshop.R
import com.atik.coffeeshop.core.sharedBoundsTransform
import com.atik.coffeeshop.features.home.explore.data.models.ItemsModel
import com.atik.coffeeshop.ui.components.AppLoadingIndicator
import com.atik.coffeeshop.ui.components.BodyText
import com.atik.coffeeshop.ui.components.CaptionText
import com.atik.coffeeshop.ui.components.PriceText


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CopyItem(
    item: ItemsModel,
    onAddClick: () -> Unit = {},
    onItemClick: () -> Unit = {},
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Box(
        modifier = Modifier
            .width(176.dp)
            .height(260.dp)
            .clip(RoundedCornerShape(18.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(18.dp))
                .background(colorResource(id = R.color.lightBrown))
                .clickable { onItemClick() }

        ) {

            Spacer(modifier = Modifier.height(75.dp))

            BodyText(
                text = item.title,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "title/${item.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(8.dp))

            CaptionText(
                text = item.description,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = colorResource(R.color.lightGray),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Price and Add Btn
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                PriceText(
                    text = "$${item.price}",
                    color = colorResource(R.color.black)
                )

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(colorResource(id = R.color.green))
                        .clickable { onAddClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add to cart",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        val imageUrl = item.picUrl.firstOrNull()
        var isLoading by remember { mutableStateOf(true) }
        var isError by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(140.dp)
                .sharedElement(
                    rememberSharedContentState(key = "image/${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = sharedBoundsTransform
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isError) {
                Image(
                    painter = painterResource(R.drawable.copy_thumbnail),
                    contentDescription = item.title,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .size(250) // exact size hint দিলে Coil সঠিক resolution-এ decode করে, faster
                        .crossfade(true)
                        .build(),
                    contentDescription = item.title,
                    contentScale = ContentScale.Fit,
                    onLoading = { isLoading = true },
                    onSuccess = { isLoading = false; isError = false },
                    onError = { isLoading = false; isError = true },
                    modifier = Modifier.fillMaxSize()
                )
                if (isLoading) {
                    AppLoadingIndicator(
                        size = 64.dp
                    )
                }
            }
        }


    }
}
