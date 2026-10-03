package com.atik.coffeeshop.features.details.presentation.widget

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.StarRate
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atik.coffeeshop.R
import com.atik.coffeeshop.core.sharedBoundsTransform
import com.atik.coffeeshop.features.home.explore.data.models.ItemsModel
import com.atik.coffeeshop.ui.components.AppLoadingIndicator
import com.atik.coffeeshop.ui.components.AuthButton
import com.atik.coffeeshop.ui.components.CaptionText
import com.atik.coffeeshop.ui.components.PriceText
import com.atik.coffeeshop.ui.components.TitleText

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailsImageSection(
    item: ItemsModel, animatedVisibilityScope: AnimatedVisibilityScope
) {
    val imageUrl = item.picUrl.firstOrNull()
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .background(colorResource(R.color.cream))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(300.dp)
                .sharedElement(
                    rememberSharedContentState(key = "image/${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = sharedBoundsTransform
                ), contentAlignment = Alignment.Center
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
                    AppLoadingIndicator(size = 120.dp)
                }
            }
        }

        Text(
            text = item.title,
            modifier = Modifier
                .fillMaxWidth()
                .sharedBounds(
                    rememberSharedContentState(key = "title/${item.id}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .padding(vertical = 12.dp),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold, color = Color.Black
            ),
            textAlign = TextAlign.Center
        )

    }
}


enum class CoffeeSize { Small, Medium, Large }

@Composable
fun CoffeeSizeSelectorSection(
    selectedSize: CoffeeSize, onSizeSelected: (CoffeeSize) -> Unit
) {
    TitleText(text = "Coffee Size", color = colorResource(R.color.black))
    Spacer(modifier = Modifier.height(8.dp))

    //var selectedSize by remember { mutableStateOf(CoffeeSize.Small) }
    val sizes = CoffeeSize.entries

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(colorResource(R.color.cream))

    ) {
        val itemWidth = maxWidth / sizes.size
        val selectedIndex = sizes.indexOf(selectedSize)

        val animatedOffset by animateDpAsState(
            targetValue = itemWidth * selectedIndex,
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
            label = "pillOffset"
        )

        // Animated Dark Brown Indicator
        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .width(itemWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(30.dp))
                .background(colorResource(R.color.darkBrown))
        )

        Row(modifier = Modifier.fillMaxSize()) {
            sizes.forEach { size ->
                val isSelected = selectedSize == size
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.Black,
                    animationSpec = tween(durationMillis = 300),
                    label = "textColor"
                )
                val textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(30.dp))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSizeSelected(size) }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = size.name,
                        color = textColor,
                        style = textStyle

                    )
                }
            }
        }

    }


}

@Composable
fun QuantityAndDetailsSection(
    item: ItemsModel
    // quantity: Int
) {
    var quantity by remember {
        mutableIntStateOf(1)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
            ) {
                TitleText(text = "Qty", color = colorResource(R.color.black))
                Spacer(modifier = Modifier.width(12.dp))

                // Increase, Decrease Btn
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .background(colorResource(R.color.cream)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Minus
                    IconButton(
                        onClick = {
                            if (quantity > 1) {
                                quantity--
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Rounded.Remove,
                            contentDescription = "Decrease quantity",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    // Quantity
                    Text(
                        text = quantity.toString(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )

                    // Plus
                    IconButton(
                        onClick = { quantity++ }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Increase quantity",
                            tint = Color.Black,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                PriceText(text = "4.5", color = colorResource(R.color.black))
                Icon(
                    imageVector = Icons.Rounded.StarRate,
                    contentDescription = "Star Icon",
                    tint = colorResource(R.color.darkBrown)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        TitleText(text = "Description", color = colorResource(R.color.black))

        Spacer(modifier = Modifier.height(8.dp))

        CaptionText(
            text = item.description,
            color = colorResource(R.color.black),
            maxLines = 6
        )
    }

}

@Composable
fun AddToCartAndPriceSection(
    item: ItemsModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {

        AuthButton(
            text = "Add to Cart",
            onClick = {},
            containerColor = colorResource(R.color.green)
        )

        PriceText(
            text = "$${item.price}",
            color = colorResource(R.color.black)
        )


    }
}
