package com.atik.coffeeshop.features.onboarding.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.R
import com.atik.coffeeshop.ui.components.CaptionText
import com.atik.coffeeshop.ui.components.OnboardingButton
import com.atik.coffeeshop.ui.components.OnboardingHeading
import com.atik.coffeeshop.ui.theme.AppTheme

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .padding(horizontal = 24.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Image(
            painter = painterResource(R.drawable.onboarding_image),
            contentDescription = "Coffee",
            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom

        ) {
            OnboardingHeading(
                text = stringResource(R.string.coffee_made_easy),
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(12.dp))
            CaptionText(
                text = stringResource(R.string.order_your_favorite_),
                color = colorResource(R.color.black),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(18.dp))
            OnboardingButton(
                text = stringResource(R.string.get_started),
                onClick = onGetStarted,
                containerColor = colorResource(R.color.lightBrown),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}
