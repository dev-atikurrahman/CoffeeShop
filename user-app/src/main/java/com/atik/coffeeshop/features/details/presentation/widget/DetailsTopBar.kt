package com.atik.coffeeshop.features.details.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.R
import com.atik.coffeeshop.ui.components.TitleText


@Composable
fun DetailsTopBar(
    isItemFavorite: Boolean, onBackClick: () -> Unit, onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {}
        ) {
            Icon(
                Icons.Rounded.ArrowBackIosNew,
                contentDescription = "Go back",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }


        Spacer(modifier = Modifier.width(8.dp))
        TitleText(
            text = "Back", modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(30.dp))
                .clickable { onFavoriteClick() },
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isItemFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isItemFavorite) Color.Red else Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

    }
}