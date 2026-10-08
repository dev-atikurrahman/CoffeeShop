package com.atik.coffeeshop.features.explore.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atik.coffeeshop.R
import com.atik.coffeeshop.features.explore.data.models.Category

@Composable
fun CategoryItem(category: Category) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))

            .background(colorResource(id = R.color.cream))
            .clickable {}
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = category.title,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}