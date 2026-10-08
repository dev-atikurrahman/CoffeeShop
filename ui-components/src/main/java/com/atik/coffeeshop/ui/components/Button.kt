package com.atik.coffeeshop.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.atik.coffeeshop.ui.R


private val ButtonShape = RoundedCornerShape(percent = 100)
private val AuthBtnShape = RoundedCornerShape(percent = 25)


@Composable
fun OnboardingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color,
    contentColor: Color = Color.White,
    shape: Shape = ButtonShape,
    contentPadding: PaddingValues = PaddingValues(horizontal = 28.dp, vertical = 12.dp)
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.7f),
        ),
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        ButtonText(text = text, color = colorResource(R.color.black))
    }
}

@Composable
fun AuthButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = colorResource(R.color.black),
    contentColor: Color = colorResource(R.color.white),
    shape: Shape = AuthBtnShape,
    contentPadding: PaddingValues = PaddingValues(horizontal = 28.dp, vertical = 12.dp)
) {
    val currentContentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.6f)

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.6f),
        ),
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        ButtonText(text = text, color = currentContentColor)
    }
}

@Composable
fun AppButton1(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = colorResource(R.color.black),
    contentColor: Color = colorResource(R.color.white),
    shape: Shape = AuthBtnShape,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
) {
    val currentContentColor = if (enabled) contentColor else contentColor.copy(alpha = 0.6f)

    TextButton(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f),
            disabledContentColor = contentColor.copy(alpha = 0.6f),
        ),
        contentPadding = contentPadding,
        modifier = modifier.height(36.dp)
    ) {
        CaptionText(text = text, color = currentContentColor)
    }
}

@Composable
fun SocialIconButton(
    iconResId: Int,
    contentDescriptor: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(ButtonShape)
            .background(colorResource(R.color.lightCream))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescriptor,
            modifier = Modifier.size(28.dp)
        )
    }

}

