package com.atik.coffeeshop.features.payment.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.atik.coffeeshop.R
import com.atik.coffeeshop.ui.components.TitleText

@Composable
fun PaymentScreen(
   onBackClick: () -> Unit,
   modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.profileBg))
            .windowInsetsPadding(WindowInsets.safeDrawing),

    ) {
        TitleText(
            text = "This is Payment",
            color = Color.Black
        )
    }

}