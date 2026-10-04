package com.atik.coffeeshop.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atik.coffeeshop.ui.R


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.darkBrown),
    containerColor: Color = Color.Transparent,
    size: Dp
) {
    LoadingIndicator(
        modifier = modifier.size(size),
        color = color
    )

}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    scrimColor: Color = Color.Black.copy(alpha = 0.35f),
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        content()

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor)
                    .pointerInput(Unit) {},
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LoadingIndicator(
                        modifier = Modifier.size(56.dp),
                        colorResource(R.color.darkBrown),
                    )

                    Spacer(modifier = Modifier.height(28.dp))
                    if (message != null) {
                        Text(
                            text = message,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = colorResource(R.color.darkBrown)
                            )
                        )
                    }
                }
            }
        }
    }
}