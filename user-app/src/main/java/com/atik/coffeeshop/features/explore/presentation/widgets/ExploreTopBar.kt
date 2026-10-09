package com.atik.coffeeshop.features.explore.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.atik.coffeeshop.R
import com.atik.coffeeshop.ui.components.HeadingText

@Composable
fun ExploreTopBar(
    userName: String,
    imageUrl: String?,
    onNotificationClick: () -> Unit,
) {

    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageUrl = imageUrl?.firstOrNull()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (isError) {
            Image(
                painterResource(R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = "User Image",
                onLoading = { isLoading = true },
                onSuccess = {
                    isLoading = false
                    isError = false
                },
                onError = {
                    isLoading = false
                    isError = true
                },
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(Modifier.width(12.dp))

        HeadingText(
            text = userName,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(colorResource(R.color.darkBrown))
                .clickable { onNotificationClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.bell_icon),
                contentDescription = "Notification",
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
