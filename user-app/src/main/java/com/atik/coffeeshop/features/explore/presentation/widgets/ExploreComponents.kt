package com.atik.coffeeshop.features.explore.presentation.widgets

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.atik.coffeeshop.R
import com.atik.coffeeshop.features.explore.data.models.BannerModel
import com.atik.coffeeshop.features.explore.data.models.Category
import com.atik.coffeeshop.features.explore.data.models.ItemsModel
import com.atik.coffeeshop.ui.components.HintText
import com.atik.coffeeshop.ui.components.TitleText
import kotlin.math.abs


@Composable
fun TopAppBarSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painterResource(R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        // Username
        Text(
            text = "Tina Anderson",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
            ),
            modifier = Modifier.weight(1f)
        )

        // Bell Icon
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(color = colorResource(R.color.darkBrown)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bell_icon),
                contentDescription = "Notification",
                modifier = Modifier.size(22.dp),
            )
        }

    }
}

@Composable
fun SearchBarSection() {
    var search by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        // Search Text field
        BasicTextField(
            value = search,
            onValueChange = { search = it },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .weight(1f),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp))
                        .background(color = colorResource(id = R.color.cream))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.darkBrown),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box {
                        if (search.isEmpty()) {
                            HintText(
                                text = "Search anything...",
                            )
                        }
                        innerTextField()
                    }

                }
            }

        )

        Spacer(modifier = Modifier.width(12.dp))

        // Filter Icon
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(id = R.color.cream))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.FilterList,
                contentDescription = null,
                tint = colorResource(id = R.color.darkBrown),
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

@Composable
fun CategorySection(categories: List<Category>) {
    Column {
        TitleText(
            text = "Category",
            color = colorResource(R.color.black)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clip(RoundedCornerShape(30.dp))
        ) {
            items(categories) { category ->
                CategoryItem(category)
            }
        }
    }
}

@SuppressLint("FrequentlyChangingValue")
@Composable
fun BannerImageSection(images: List<BannerModel>) {
    if (images.isEmpty()) return

    val pagerState = rememberPagerState(
        pageCount = { images.size },
        initialPage = 0
    )

    // Image banner
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scale = 1f - (0.2f * abs(pageOffset))
            var isLoading by remember { mutableStateOf(true) }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            ) {
                AsyncImage(
                    model = images[page].url,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    onSuccess = { isLoading = false },
                    onError = { isLoading = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable {}

                )
            }
        }

        /*if (images.size > 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                repeat(images.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                if (pagerState.currentPage == index) {
                                    Color.White
                                } else {
                                    Color.Gray
                                }
                            )
                    )
                }
            }
        }*/

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.PopularCopySection(
    popularCopies: List<ItemsModel>,
    onItemClick: (ItemsModel) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TitleText(
            text = "Popular Coffees",
            color = colorResource(R.color.black)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = popularCopies,
                key = { item -> item.id }
            ) { item ->
                CopyItem(
                    item = item,
                    onAddClick = { /* TODO: cart */ },
                    onItemClick = { onItemClick(item) },
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }

    }
}
